package az.baku.divfinalproject.controller;

import az.baku.divfinalproject.dto.request.LoginRequest;
import az.baku.divfinalproject.dto.request.RegisterRequest;
import az.baku.divfinalproject.handler.GlobalHandler;
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

    @PostMapping("/sign-in")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return userService.authenticateUser(loginRequest);
    }

    @PostMapping("/registration-email")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest userRequest) {
        return userService.registerUserWithEmail(userRequest);
    }

    @PostMapping("/registration-phoneNumber")
    public ResponseEntity<?> registerWithPhoneNumber(@Valid @RequestBody RegisterRequest signUpRequest) {
        return userService.registerWithPhoneNumber(signUpRequest);
    }
}