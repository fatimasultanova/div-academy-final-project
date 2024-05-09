package az.baku.divfinalproject.controller;

import az.baku.divfinalproject.dto.request.LoginRequest;
import az.baku.divfinalproject.dto.request.RegisterRequest;
import az.baku.divfinalproject.repository.UserRepository;
import az.baku.divfinalproject.dto.response.MessageResponse;
import az.baku.divfinalproject.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final UserRepository userRepository;


    @PostMapping("/sign-in")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return userService.authenticateUser(loginRequest);
    }


    @PostMapping("/register-email")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest userRequest) {
        return userService.registerUserWithEmail(userRequest);
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