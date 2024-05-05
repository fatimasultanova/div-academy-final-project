package az.baku.divfinalproject.controller;

import az.baku.divfinalproject.dto.request.LoginRequest;
import az.baku.divfinalproject.dto.request.RegisterRequest;
import az.baku.divfinalproject.entity.User;
import az.baku.divfinalproject.repository.UserRepository;
import az.baku.divfinalproject.security.services.AuthenticationServiceImpl;
import az.baku.divfinalproject.dto.response.MessageResponse;
import az.baku.divfinalproject.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    final
    UserService userService;

    final
    UserRepository userRepository;

    final AuthenticationServiceImpl authenticationServiceImpl;

    public AuthController(UserRepository userRepository, UserService userService, AuthenticationServiceImpl authenticationServiceImpl) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.authenticationServiceImpl = authenticationServiceImpl;
    }

    @PostMapping("/sign-in")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Optional<User> byPhoneNumberOrEmail = userRepository.findByPhoneNumberOrEmail(loginRequest.getPhoneNumberOrEmail());
        if (byPhoneNumberOrEmail.isPresent()) {
            User user = byPhoneNumberOrEmail.get();
            if (user.getPhoneNumber()!=null && !(user.isBlockedByAdmin())){
                return authenticationServiceImpl.authenticate(loginRequest);
            }else if (user.isActive() && !(user.isBlockedByAdmin())){
                return authenticationServiceImpl.authenticate(loginRequest);
            }
        }
        return ResponseEntity.badRequest().eTag("Not Verified").build();
    }


    @PostMapping("/register-email")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest userRequest) {
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        userService.registerWithEmail(userRequest);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }


    @PostMapping("/register-phoneNumber")
    public ResponseEntity<?> registerWithPhoneNumber(@Valid @RequestBody RegisterRequest signUpRequest) {
        if (userRepository.existsByPhoneNumber(signUpRequest.getPhoneNumber())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: PhoneNumber is already taken!"));
        }

        if (!(signUpRequest.getPhoneNumber().startsWith("+"))) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: PhoneNumber isn't true! (Incorrect format) (It must start with [+] and only numeric characters)"));
        }

        userService.registerWithPhoneNumber(signUpRequest);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}