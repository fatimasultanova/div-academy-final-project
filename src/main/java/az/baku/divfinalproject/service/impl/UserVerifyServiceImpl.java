package az.baku.divfinalproject.service.impl;

import az.baku.divfinalproject.dto.request.EmailDTO;
import az.baku.divfinalproject.dto.response.ExceptionResponse;
import az.baku.divfinalproject.entity.UserVerify;
import az.baku.divfinalproject.exception.ApplicationException;
import az.baku.divfinalproject.exception.ExceptionEnums;
import az.baku.divfinalproject.repository.UserRepository;
import az.baku.divfinalproject.repository.UserVerifyRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import az.baku.divfinalproject.entity.User;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserVerifyServiceImpl {
    private static final Logger logger = LoggerFactory.getLogger(UserVerifyServiceImpl.class);
    private final UserVerifyRepository userVerifyRepository;
    private final UserRepository userRepository;
    private final RabbitTemplate rabbitTemplate;
    @Value("${rabbitmq.exchange.email.name}")
    private String emailExchange;

    @Value("${rabbitmq.binding.email.name}")
    private String emailRoutingKey;


    public Optional<UserVerify> findByToken(String token) {
        return userVerifyRepository.findByToken(token);
    }

    public List<UserVerify> findByUserId(long userId) {
        return userVerifyRepository.findAllByUser_Id(userId);
    }

    public String verifyEmail(String token) {
        logger.info("Verification process started for token: {}", token);
        Optional<UserVerify> byToken = findByToken(token);
        if (byToken.isPresent() && !(byToken.get().getUser().isBlockedByAdmin())) {
            UserVerify userVerify = byToken.get();
            if (userVerify.isTokenValid()) {
                User user = userVerify.getUser();
                user.setActive(true);
                userRepository.save(user);
            } else {
                throw new ApplicationException(new ExceptionResponse(ExceptionEnums.TOKEN_EXPIRED.getMessage(), HttpStatus.BAD_REQUEST));
            }
        } else {
            throw new ApplicationException(new ExceptionResponse(ExceptionEnums.TOKEN_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND));
        }
        byToken.get().setActive(false);
        userVerifyRepository.save(byToken.get());
        logger.info("Verification process completed for token: {}", token);
        return "Verified Successfully";
    }

    public String resendEmail(String token) {
        logger.info("Resend process started for token: {}", token);
        Optional<UserVerify> byToken = findByToken(token);
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
        } else {
            throw new ApplicationException(new ExceptionResponse(ExceptionEnums.TOKEN_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND));
        }
        byToken.get().setActive(false);
        userVerifyRepository.save(byToken.get());
        logger.info("Resend process completed for token: {}", token);
        return "Resend Successfully";
    }
}
