package az.baku.divfinalproject.service.impl;

import az.baku.divfinalproject.dto.request.*;
import az.baku.divfinalproject.dto.response.ExceptionResponse;
import az.baku.divfinalproject.dto.response.MessageResponse;
import az.baku.divfinalproject.dto.response.UserResponse;
import az.baku.divfinalproject.entity.Role;
import az.baku.divfinalproject.entity.User;
import az.baku.divfinalproject.entity.UserVerify;
import az.baku.divfinalproject.exception.ApplicationException;
import az.baku.divfinalproject.exception.ExceptionEnums;
import az.baku.divfinalproject.mapper.UserMapper;
import az.baku.divfinalproject.repository.RoleRepository;
import az.baku.divfinalproject.repository.UserRepository;
import az.baku.divfinalproject.repository.UserVerifyRepository;
import az.baku.divfinalproject.security.services.AuthenticationServiceImpl;
import az.baku.divfinalproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserVerifyRepository userVerifyRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final RabbitTemplate rabbitTemplate;
    private final AuthenticationServiceImpl authenticationService;

    @Value("${rabbitmq.exchange.email.name}")
    private String emailExchange;

    @Value("${rabbitmq.binding.email.name}")
    private String emailRoutingKey;


    public UserResponse createWithEmail(RegisterRequest request) {
        User user = createdUser(request);
        user.setEmail(request.getEmail());

        UserVerify userVerify = new UserVerify();
        userVerify.setUser(user);

        User saved = userRepository.save(user);

        userVerifyRepository.save(userVerify);

        rabbitTemplate.convertAndSend(emailExchange,
                emailRoutingKey,
                EmailDTO.builder()
                        .subject("Verify your email")
                        .body("localhost:8080/api/user/verify-email/" + userVerify.getToken())
                        .to(request.getEmail())
                        .build());

        return userMapper.toResponse(saved);
    }


    public UserResponse createWithPhoneNumber(RegisterRequest request) {
        User user = createdUser(request);
        user.setPhoneNumber(request.getPhoneNumber());

        User saved = userRepository.save(user);
        return userMapper.toResponse(saved);
    }

    private User createdUser(RegisterRequest request) {
        User user = new User(request.getFirstName(),
                request.getMiddleName(),
                request.getLastName(),
                request.getBirthDate(),
                encoder.encode(request.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(userRole);

        user.setRoles(roles);
        return user;
    }


    @Override
    public UserResponse create(UserRequest userRequest) {
        return null;
    }


    @Override
    public UserResponse update(long id, UserRequest request) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Error: User is not found."));
        return userMapper.toResponse(userRepository.save(userMapper.partialUpdate(request, user)));
    }

    @Override
    public void delete(long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Error: User is not found."));
        user.setDeleted(true);
        userRepository.save(user);
    }

    @Override
    public UserResponse getById(long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Error: User is not found."));
        return userMapper.toResponse(user);
    }

    @Override
    public Collection<UserResponse> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream().map(userMapper::toResponse).collect(Collectors.toList());
    }


    @Override
    public UserResponse getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Error: User is not found."));
        return userMapper.toResponse(user);
    }

    @Override
    public UserResponse getUserByPhoneNumber(String phoneNumber) {
        User user = userRepository.findByPhoneNumber(phoneNumber).orElseThrow(() -> new RuntimeException("Error: User is not found."));
        return userMapper.toResponse(user);
    }

    @Override
    public UserResponse getUserByEmailAndPassword(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Error: User is not found."));
        boolean equals = user.getPassword().equals(password);
        if (equals) {
            return userMapper.toResponse(user);
        } else {
            return null;
        }
    }

    @Override
    public UserResponse getUserByPhoneNumberAndPassword(String username, String password) {
        return null;
    }

    private String mapRolesToString(Set<Role> roles) {
        return roles.stream()
                .map(Role::getName)
                .collect(Collectors.joining(", "));
    }

    @Override
    public ResponseEntity<?> deleteUserByAdmin(Request<UserRequest> request) {
        Optional<User> userOptional = userRepository.findById(request.getId());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.isDeleted()) {
                user.setDeleted(false);
                userRepository.save(user);
                return new ResponseEntity<>("User returned successfully!", HttpStatus.OK);
            } else {
                user.setDeleted(true);
                userRepository.save(user);
                return new ResponseEntity<>("User deleted successfully!", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<?> blockUserByAdmin(Request<UserRequest> request) {
        Optional<User> user = userRepository.findById(request.getId());
        if (user.isPresent()) {
            if (user.get().isBlockedByAdmin()) {
                user.get().setBlockedByAdmin(false);
                userRepository.save(user.get());
                return new ResponseEntity<>("User unblocked successfully!", HttpStatus.OK);
            } else {
                user.get().setBlockedByAdmin(true);
                userRepository.save(user.get());
                return new ResponseEntity<>("User blocked successfully!", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<?> cancelUserSubscriptionByAdmin(Request<UserRequest> request) {
        Optional<User> user = userRepository.findById(request.getId());
        if (user.isPresent()) {
            user.get().setSubscription(null);
            userRepository.save(user.get());
            return new ResponseEntity<>("User`s subscription canceled successfully!", HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<?> updateUserByAdmin(Request<RegisterRequest> request) {
        Optional<User> user = userRepository.findById(request.getId());
        if (user.isPresent()) {
            userMapper.partialUpdate(request.getRequest(), user.get());
            userRepository.save(user.get());
            return ResponseEntity.ok(new MessageResponse("User updated successfully!"));
        }
        return new ResponseEntity<>("User not updated!", HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<?> activateUserByAdmin(Request<UserRequest> request) {
        Optional<User> user = userRepository.findById(request.getId());
        if (user.isPresent()) {
            user.get().setActive(true);
            userRepository.save(user.get());
            return ResponseEntity.ok(userMapper.toResponse(user.get()));
        }
        return new ResponseEntity<>("User not activate!", HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<?> authenticateUser(LoginRequest loginRequest) {
        logger.info("Authenticating user process stared with phone number or email: {}", loginRequest.getPhoneNumberOrEmail());
        Optional<User> byPhoneNumberOrEmail = userRepository.findByPhoneNumberOrEmail(loginRequest.getPhoneNumberOrEmail());
        if (byPhoneNumberOrEmail.isPresent()) {
            User user = byPhoneNumberOrEmail.get();
            if ((user.getPhoneNumber() != null || user.getEmail() != null) && !(user.isBlockedByAdmin()) && !(user.isDeleted())) {
                if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                    return authenticationService.authenticate(loginRequest);
                }else {
                    throw new ApplicationException(new ExceptionResponse(ExceptionEnums.ACCESS_DENIED.getMessage(), HttpStatus.NO_CONTENT));
                }
            } else if (user.isActive() && !(user.isBlockedByAdmin()) && !(user.isDeleted())) {
                if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                    return authenticationService.authenticate(loginRequest);
                } else {
                    throw new ApplicationException(new ExceptionResponse(ExceptionEnums.ACCESS_DENIED.getMessage(), HttpStatus.NO_CONTENT));
                }
            }
        }
        logger.error("User with phone number or email: {} not found or not eligible for authentication", loginRequest.getPhoneNumberOrEmail());
        return ResponseEntity.badRequest().body("User not authenticated!");
    }

    @Override
    public ResponseEntity<?> registerUserWithEmail(RegisterRequest userRequest) {
        logger.info("Registering user with email: {}", userRequest.getEmail());

        if (userRepository.existsByEmail(userRequest.getEmail())) {
            logger.error("Email {} is already in use", userRequest.getEmail());
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }
        UserResponse response = createWithEmail(userRequest);

        logger.info("User registered successfully with email: {}", response.getEmail());
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @Override
    public ResponseEntity<?> registerWithPhoneNumber(RegisterRequest signUpRequest) {
        logger.info("Registering user with phone number: {}", signUpRequest.getPhoneNumber());

        if (userRepository.existsByPhoneNumber(signUpRequest.getPhoneNumber())) {
            logger.error("Phone number {} is already taken", signUpRequest.getPhoneNumber());
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: PhoneNumber is already taken!"));
        }
        if (!(signUpRequest.getPhoneNumber().startsWith("+"))) {
            logger.error("Incorrect format for phone number: {}", signUpRequest.getPhoneNumber());
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: PhoneNumber isn't true! (Incorrect format) (It must start with [+] and only contain numeric characters)"));
        }

        UserResponse response = createWithPhoneNumber(signUpRequest);

        logger.info("User registered successfully with phone number: {}", response.getPhoneNumber());
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));

    }

}
