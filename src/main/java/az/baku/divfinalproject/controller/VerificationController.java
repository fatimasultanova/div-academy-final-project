package az.baku.divfinalproject.controller;

import az.baku.divfinalproject.entity.UserVerify;
import az.baku.divfinalproject.repository.UserRepository;
import az.baku.divfinalproject.service.impl.UserVerifyServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class VerificationController {
    private final UserVerifyServiceImpl userVerifyService;
    private final UserRepository userRepository;

    public VerificationController(UserVerifyServiceImpl userVerifyService, UserRepository userRepository) {
        this.userVerifyService = userVerifyService;
        this.userRepository = userRepository;
    }

    @GetMapping("/verify-email/{token}")
    public String verify(@PathVariable String token) {
        Optional<UserVerify> byToken = userVerifyService.findByToken(token);
        if (byToken.isPresent()) {
            UserVerify userVerify = byToken.get();
            if (userVerify.isTokenValid()) {
                userVerify.getUser().setActive(true);

                userRepository.save(userVerify.getUser());
            }
        }
        return "Verified Successfully";
    }
}