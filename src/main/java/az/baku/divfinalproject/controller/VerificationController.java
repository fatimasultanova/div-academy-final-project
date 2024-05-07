package az.baku.divfinalproject.controller;

import az.baku.divfinalproject.dto.request.EmailDTO;
import az.baku.divfinalproject.entity.User;
import az.baku.divfinalproject.entity.UserVerify;
import az.baku.divfinalproject.repository.UserRepository;
import az.baku.divfinalproject.repository.UserVerifyRepository;
import az.baku.divfinalproject.service.impl.UserVerifyServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class VerificationController {
    private final UserVerifyServiceImpl userVerifyService;
    private final UserRepository userRepository;
    private final UserVerifyRepository userVerifyRepository;
    private final RabbitTemplate rabbitTemplate;
    @Value("${rabbitmq.exchange.email.name}")
    private String emailExchange;

    @Value("${rabbitmq.binding.email.name}")
    private String emailRoutingKey;


    @GetMapping("/verify-email/{token}")
    public String verify(@PathVariable String token) {
        Optional<UserVerify> byToken = userVerifyService.findByToken(token);
        if (byToken.isPresent() && !(byToken.get().getUser().isBlockedByAdmin())) {
            UserVerify userVerify = byToken.get();
            if (userVerify.isTokenValid()) {
                User user = userVerify.getUser();
                user.setActive(true);
                userRepository.save(user);
            }else {
                return "Token expired";
            }
        }else {
            return "Token not found";
        }
        byToken.get().setActive(false);
        userVerifyRepository.save(byToken.get());
        return "Verified Successfully";
    }

    @GetMapping("/resend-email/{token}")
    public String resend(@PathVariable String token) {
        Optional<UserVerify> byToken = userVerifyService.findByToken(token);
        if (byToken.isPresent() && !(byToken.get().getUser().isBlockedByAdmin())) {
            UserVerify userVerify = new UserVerify();
            User user = byToken.get().getUser();
            userVerify.setUser(byToken.get().getUser());
            userVerifyRepository.save(userVerify);
            rabbitTemplate.convertAndSend(emailExchange,
                    emailRoutingKey,
                    EmailDTO.builder()
                            .subject("Verify your email")
                            .body("localhost:8080/api/user/verify-email/" + userVerify.getToken())
                            .to(user.getEmail())
                            .build());
        }else {
            return "Token not found";
        }
        byToken.get().setActive(false);
        userVerifyRepository.save(byToken.get());
        return "Resend Successfully";
    }


}