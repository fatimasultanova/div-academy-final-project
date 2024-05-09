package az.baku.divfinalproject.service.impl;

import az.baku.divfinalproject.dto.request.*;
import az.baku.divfinalproject.dto.response.ExceptionResponse;
import az.baku.divfinalproject.dto.response.MessageResponse;
import az.baku.divfinalproject.dto.response.UserResponse;
import az.baku.divfinalproject.entity.*;
import az.baku.divfinalproject.exception.ApplicationException;
import az.baku.divfinalproject.exception.ExceptionEnums;
import az.baku.divfinalproject.mapper.UserMapper;
import az.baku.divfinalproject.repository.*;
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
    private final AdvertRepository advertRepository;
    private final UsersSubscriptionsCountingRepository countingRepository;

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

        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        user.getRoles().add(userRole);
        user.setRoles(user.getRoles());
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
                } else {
                    throw new ApplicationException(new ExceptionResponse(ExceptionEnums.ACCESS_DENIED.getMessage(), HttpStatus.BAD_REQUEST));
                }
            } else if (user.isActive() && !(user.isBlockedByAdmin()) && !(user.isDeleted())) {
                if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                    return authenticationService.authenticate(loginRequest);
                } else {
                    throw new ApplicationException(new ExceptionResponse(ExceptionEnums.ACCESS_DENIED.getMessage(), HttpStatus.BAD_REQUEST));
                }
            }
        }
        logger.error("User with phone number or email: {} not found or not eligible for authentication", loginRequest.getPhoneNumberOrEmail());
        throw new ApplicationException(new ExceptionResponse(ExceptionEnums.USERNAME_OR_PASSWORD_IS_WRONG.getMessage(), HttpStatus.BAD_REQUEST));
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

    @Override
    public ResponseEntity<?> getContactInformation(Request<AdvertRequest> request) {
        logger.info("Fetching contact information for user id: {}", request.getId());

        Optional<User> user = userRepository.findById(request.getId());
        if (user.isPresent()) {
            logger.info("User found with id: {}", user.get().getId());

            Optional<UsersSubscriptionsCounting> byUser = countingRepository.findByUser(user.get());
            Optional<Advert> advert = advertRepository.findById(request.getRequest().getId());
            if (advert.isPresent()) {
                logger.info("Advert found with id: {}", advert.get().getId());

                if (user.get().getViewedAdverts().contains(advert.get())) {
                    logger.info("User already viewed the advert with id: {}", advert.get().getId());
                    return ResponseEntity.ok(userMapper.toResponse(user.get()));
                }

                if (byUser.isPresent()) {
                    logger.info("Subscription information found for user: {}", user.get().getId());

                    if (byUser.get().getCount() >= 1) {
                        logger.info("User has remaining subscription count: {}", byUser.get().getCount());
                        user.get().getViewedAdverts().add(advert.get());
                        byUser.get().setCount(byUser.get().getCount() - 1);
                        countingRepository.save(byUser.get());
                        userRepository.save(user.get());
                        return ResponseEntity.ok(userMapper.toResponse(user.get()));
                    } else {
                        logger.warn("User's subscription is finished");
                        return ResponseEntity.badRequest().body(new MessageResponse("Error: Subscription is finished!"));
                    }
                } else {
                    logger.warn("No subscription information found for user: {}", user.get().getId());
                    return ResponseEntity.badRequest().body(new MessageResponse("Error: Subscription not found!"));
                }
            } else {
                logger.warn("Advert not found with id: {}", request.getRequest().getId());
                return ResponseEntity.badRequest().body(new MessageResponse("Error: Advert not found!"));
            }
        } else {
            logger.warn("User not found with id: {}", request.getId());
            throw new ApplicationException(new ExceptionResponse(ExceptionEnums.USER_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND));
        }
    }

    public ResponseEntity<?> updateEmail(Request<LoginRequest> updateRequest) {
        logger.info("Updating email for user id: {}", updateRequest.getId());

        Optional<User> user = userRepository.findById(updateRequest.getId());
        if (user.isPresent()) {
            logger.debug("User found with id: {}", user.get().getId());

            user.get().setEmail(updateRequest.getRequest().getPhoneNumberOrEmail());
            user.get().setActive(false);

            UserVerify userVerify = new UserVerify();
            userVerify.setUser(user.get());

            userVerifyRepository.save(userVerify);
            rabbitTemplate.convertAndSend(emailExchange,
                    emailRoutingKey,
                    EmailDTO.builder()
                            .subject("Verify your email")
                            .body("localhost:8080/api/user/verify-email/" + userVerify.getToken())
                            .to(updateRequest.getRequest().getPhoneNumberOrEmail())
                            .build());
            userRepository.save(user.get());
            logger.info("Email updated successfully for user id: {}", user.get().getId());
            return ResponseEntity.ok(new MessageResponse("Email updated successfully"));
        } else {
            logger.debug("User not found with id: {}", updateRequest.getId());
            throw new ApplicationException(new ExceptionResponse(ExceptionEnums.USER_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND));
        }
    }

    public ResponseEntity<?> updatePhone(Request<LoginRequest> updateRequest) {
        logger.info("Updating phone number for user id: {}", updateRequest.getId());

        Optional<User> user = userRepository.findById(updateRequest.getId());
        if (user.isPresent()) {
            logger.debug("User found with id: {}", user.get().getId());

            user.get().setPhoneNumber(updateRequest.getRequest().getPhoneNumberOrEmail());
            userRepository.save(user.get());
            logger.info("Phone number updated successfully for user id: {}", user.get().getId());
            return ResponseEntity.ok(new MessageResponse("Phone number updated successfully"));
        } else {
            logger.warn("User not found with id: {}", updateRequest.getId());
            throw new ApplicationException(new ExceptionResponse(ExceptionEnums.USER_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND));
        }
    }

    public ResponseEntity<?> updatePassword(Request<PasswordRequest> request) {
        logger.info("Updating password for user id: {}", request.getId());
        Optional<User> user = userRepository.findById(request.getId());
        if (user.isPresent()) {
            if (user.get().getPassword().equals(passwordEncoder.encode(request.getRequest().getOldPassword()))) {
                if (request.getRequest().getNewPassword().equals(request.getRequest().getConfirmPassword())) {
                    user.get().setPassword(passwordEncoder.encode(request.getRequest().getNewPassword()));
                    userRepository.save(user.get());
                    logger.info("Password updated successfully for user id: {}", user.get().getId());
                } else {
                    logger.warn("New passwords do not match");
                    return ResponseEntity.badRequest().body("New passwords do not match");
                }
            }else {
                logger.warn("Password is not correct");
                throw new ApplicationException(new ExceptionResponse(ExceptionEnums.PASSWORD_INCORRECT.getMessage(), HttpStatus.BAD_REQUEST));
            }
        }
        logger.warn("User not found with id: {}", request.getId());
        throw new ApplicationException(new ExceptionResponse(ExceptionEnums.USER_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<?> setUserAdmin(Request<LoginRequest> request) {
        Optional<User> byPhoneNumberOrEmail = userRepository.findByPhoneNumberOrEmail(request.getRequest().getPhoneNumberOrEmail());
        if (byPhoneNumberOrEmail.isPresent()) {
            User user = byPhoneNumberOrEmail.get();
            Optional<Role> byName = roleRepository.findByName("ROLE_ADMIN");
            user.getRoles().add(byName.get());
            user.setRoles(user.getRoles());
            userRepository.save(user);
            logger.info("User admin updated successfully for user id: {}", user.getId());
            return ResponseEntity.ok(new MessageResponse("User admin updated successfully"));
        }
        logger.warn("User not found with id: {}", request.getId());
        throw new ApplicationException(new ExceptionResponse(ExceptionEnums.USER_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND));
    }
}