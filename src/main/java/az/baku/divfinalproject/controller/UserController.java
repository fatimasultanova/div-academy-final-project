package az.baku.divfinalproject.controller;

import az.baku.divfinalproject.dto.request.Request;
import az.baku.divfinalproject.dto.request.UserRequest;
import az.baku.divfinalproject.dto.response.MessageResponse;
import az.baku.divfinalproject.entity.Advert;
import az.baku.divfinalproject.entity.User;
import az.baku.divfinalproject.entity.UsersSubscriptionsCounting;
import az.baku.divfinalproject.mapper.UserMapper;
import az.baku.divfinalproject.repository.AdvertRepository;
import az.baku.divfinalproject.repository.UserRepository;
import az.baku.divfinalproject.repository.UsersSubscriptionsCountingRepository;
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
    private final UsersSubscriptionsCountingRepository countingRepository;


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
    public ResponseEntity<?> getContactInformation(@RequestBody Request<Advert> request) {
        Optional<User> user = repository.findById(request.getId());
        if (user.isPresent()) {
            Optional<UsersSubscriptionsCounting> byUser = countingRepository.findByUser(user.get());
            Optional<Advert> advert = advertRepository.findById(request.getRequest().getId());
            if (advert.isPresent()) {
                if (user.get().getViewedAdverts().contains(advert.get())) {
                    return ResponseEntity.ok(userMapper.toResponse(user.get()));
                }
                if (byUser.isPresent()) {
                    if (byUser.get().getCount() >= 1) {
                            user.get().getViewedAdverts().add(advert.get());
                            byUser.get().setCount(byUser.get().getCount() - 1);
                            countingRepository.save(byUser.get());
                            repository.save(user.get());
                            return ResponseEntity.ok(userMapper.toResponse(user.get()));
                        }
                    }else {
                    return ResponseEntity
                            .badRequest()
                            .body(new MessageResponse("Error: Subscription is finished!"));
                }
                } else {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Advert is not found!"));
            }
        }
            return ResponseEntity.notFound().build();
    }
}