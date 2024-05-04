package az.baku.divfinalproject.service.impl;

import az.baku.divfinalproject.entity.UserVerify;
import az.baku.divfinalproject.repository.UserVerifyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@RequiredArgsConstructor
public class UserVerifyServiceImpl{
    private final UserVerifyRepository userVerifyRepository;

    public Optional<UserVerify> findByToken(String token) {
       return userVerifyRepository.findByToken(token);
    }


}
