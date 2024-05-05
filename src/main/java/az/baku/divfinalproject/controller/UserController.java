package az.baku.divfinalproject.controller;

import az.baku.divfinalproject.dto.request.Request;
import az.baku.divfinalproject.dto.request.UserRequest;
import az.baku.divfinalproject.dto.response.MessageResponse;
import az.baku.divfinalproject.dto.response.UserResponse;
import az.baku.divfinalproject.entity.Advert;
import az.baku.divfinalproject.entity.User;
import az.baku.divfinalproject.mapper.UserMapper;
import az.baku.divfinalproject.repository.AdvertRepository;
import az.baku.divfinalproject.repository.UserRepository;
import az.baku.divfinalproject.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final UserRepository repository;
    private final AdvertRepository advertRepository;


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
    public ResponseEntity<UserResponse> getContactInformation(@RequestBody Request<Advert> request) {
        Optional<User> user = repository.findById(request.getId());
        if (user.isPresent()) {
            Optional<Advert> byId = advertRepository.findById(request.getRequest().getId());
            user.get().getViewedAdverts().add(byId.get());
            repository.save(user.get());
            return ResponseEntity.ok(userMapper.toResponse(user.get()));
        }
        return ResponseEntity.notFound().build();
    }
}