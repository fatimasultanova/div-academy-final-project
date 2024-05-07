package az.baku.divfinalproject.controller;

import az.baku.divfinalproject.dto.request.RegisterRequest;
import az.baku.divfinalproject.dto.response.UserResponse;
import az.baku.divfinalproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/admin-panel")
@RequiredArgsConstructor
@Secured("ROLE_ADMIN")
public class AdminPanelController {
    private final UserService userService;
    private final AuthController authController;

    @GetMapping("/get-all-users")
    public ResponseEntity<?> getAllUsers() {
        Collection<UserResponse> all = userService.findAll();
        return ResponseEntity.ok(all);
    }

    @PostMapping("/create-user-email")
    public ResponseEntity<?> createUserEmail(@RequestBody RegisterRequest request) {
//        UserResponse userResponse = userService.registerWithEmail(request);
//        return ResponseEntity.ok(userResponse);
        return ResponseEntity.ok(authController.registerUser(request));
    }

    @PostMapping("/create-user-phone")
    public ResponseEntity<?> createUserPhone(@RequestBody RegisterRequest request) {
        UserResponse userResponse = userService.registerWithPhoneNumber(request);
        return ResponseEntity.ok(userResponse);
    }
}
