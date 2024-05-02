package az.baku.divfinalproject.controller;

import az.baku.divfinalproject.dto.request.UpdateRequest;
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
    public ResponseEntity<?> update(@Valid @RequestBody UpdateRequest updateRequest) {
        userService.update(updateRequest.getId(),updateRequest.getUserRequest());
        return ResponseEntity.ok(new MessageResponse("User updated successfully!"));
    }



    @PostMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable long id) {
        userService.delete(id);
        return ResponseEntity.ok(new MessageResponse("User deleted successfully!"));
    }




}
