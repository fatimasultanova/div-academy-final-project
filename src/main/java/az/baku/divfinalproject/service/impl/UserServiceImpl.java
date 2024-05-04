package az.baku.divfinalproject.service.impl;

import az.baku.divfinalproject.dto.request.EmailDTO;
import az.baku.divfinalproject.dto.request.RegisterRequest;
import az.baku.divfinalproject.dto.request.UserRequest;
import az.baku.divfinalproject.dto.response.UserResponse;
import az.baku.divfinalproject.entity.Role;
import az.baku.divfinalproject.entity.User;
import az.baku.divfinalproject.entity.UserVerify;
import az.baku.divfinalproject.mapper.UserMapper;
import az.baku.divfinalproject.repository.RoleRepository;
import az.baku.divfinalproject.repository.UserRepository;
import az.baku.divfinalproject.repository.UserVerifyRepository;
import az.baku.divfinalproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final UserVerifyRepository userVerifyRepository;
    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.email.name}")
    private String emailExchange;

    @Value("${rabbitmq.binding.email.name}")
    private String emailRoutingKey;


    public UserResponse registerWithEmail(RegisterRequest request) {
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


    public UserResponse registerWithPhoneNumber(RegisterRequest request) {
        User user = createdUser(request);
        user.setPhoneNumber(request.getPhoneNumber());

        User saved = userRepository.save(user);
        return userMapper.toResponse(saved);
    }

    public User createdUser(RegisterRequest request) {
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
        return userMapper.toResponse(userRepository.save(userMapper.partialUpdate(user, request)));
    }

    @Override
    public void delete(long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Error: User is not found."));
        user.setDeleted(true);
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
}
