package az.baku.divfinalproject.controller;

import az.baku.divfinalproject.dto.request.Request;
import az.baku.divfinalproject.dto.request.UserRequest;
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
    final
    UserService userService;

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
}