package az.baku.divfinalproject.controller;

import az.baku.divfinalproject.dto.request.UserRequest;
import az.baku.divfinalproject.dto.response.MessageResponse;
import az.baku.divfinalproject.security.services.UserDetailsImpl;
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
    UserDetailsImpl userDetails;

    @PostMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable long id, @Valid @RequestBody UserRequest userRequest) {
        userService.update(id,userRequest);
        return ResponseEntity.ok(new MessageResponse("User updated successfully!"));
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable long id) {
        userService.delete(id);
        return ResponseEntity.ok(new MessageResponse("User deleted successfully!"));
    }




}
