package az.baku.divfinalproject.controller;

import az.baku.divfinalproject.service.impl.UserVerifyServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class VerificationController {
    private final UserVerifyServiceImpl userVerifyService;

    @GetMapping("/verify-email/{token}")
    public String verify(@PathVariable String token) {
       return userVerifyService.verifyEmail(token);
    }

    @GetMapping("/resend-email/{token}")
    public String resend(@PathVariable String token) {
        return userVerifyService.resendEmail(token);
    }
}