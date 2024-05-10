package az.baku.divfinalproject.controller;

import az.baku.divfinalproject.dto.request.*;
import az.baku.divfinalproject.dto.response.MessageResponse;
import az.baku.divfinalproject.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> getUserById(@RequestBody Request<UserRequest> request) {
        return ResponseEntity.ok(userService.getById(request.getId()));
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(@Valid @RequestBody Request<UserRequest> request) {
        userService.update(request.getId(), request.getRequest());
        return ResponseEntity.ok(new MessageResponse("User updated successfully!"));
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteUser(@Valid @RequestBody Request<UserRequest> request) {
        userService.delete(request.getId());
        return ResponseEntity.ok(new MessageResponse("User deleted successfully!"));
    }

    @GetMapping("/contact-information")
    public ResponseEntity<?> getContactInformation(@RequestBody Request<AdvertRequest> request) {
        return userService.getContactInformation(request);
    }

    @PutMapping("/update-email")
    public ResponseEntity<?> updateEmail(@RequestBody Request<LoginRequest> request) {
        return userService.updateEmail(request);
    }

    @PutMapping("/update-phone")
    public ResponseEntity<?> updatePhone(@RequestBody Request<LoginRequest> request) {
        return userService.updatePhone(request);
    }

    @PutMapping
    public ResponseEntity<?> updatePassword(@RequestBody Request<PasswordRequest> request) {
        return userService.updatePassword(request);
    }
}