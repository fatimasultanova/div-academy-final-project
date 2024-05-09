package az.baku.divfinalproject.controller;

import az.baku.divfinalproject.dto.request.RegisterRequest;
import az.baku.divfinalproject.dto.request.Request;
import az.baku.divfinalproject.dto.request.UserRequest;
import az.baku.divfinalproject.dto.response.UserResponse;
import az.baku.divfinalproject.mapper.UserMapper;
import az.baku.divfinalproject.repository.UserRepository;
import az.baku.divfinalproject.service.UserService;
import jakarta.validation.Valid;
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
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @GetMapping("/get-all-users")
    //@PreAuthorize()
    public ResponseEntity<?> getAllUsers() {
        Collection<UserResponse> all = userService.findAll();
        return ResponseEntity.ok(all);
    }

    @PostMapping("/activate-user")
    public ResponseEntity<?> activateUserPhone(@RequestBody Request<UserRequest> request) {
        return userService.activateUserByAdmin(request);
    }

    @PostMapping("/update-user")
    public ResponseEntity<?> update(@Valid @RequestBody Request<RegisterRequest> request) {
       return userService.updateUserByAdmin(request);
    }

    @PutMapping("/block-user")
    public ResponseEntity<?> blockUser(@Valid @RequestBody Request<UserRequest> request) {
        return userService.blockUserByAdmin(request);
    }

    @PutMapping("/delete-user")
    public ResponseEntity<?> deleteUser(@Valid @RequestBody Request<UserRequest> request) {
       return userService.deleteUserByAdmin(request);
    }

    @PutMapping("/cancel-user-subscription")
    public ResponseEntity<?> cancelUserSubscription(@Valid @RequestBody Request<UserRequest> request) {
        return userService.cancelUserSubscriptionByAdmin(request);
    }
}
