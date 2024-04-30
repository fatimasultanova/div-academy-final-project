package az.baku.divfinalproject.service;

import az.baku.divfinalproject.dto.request.RegisterRequest;
import az.baku.divfinalproject.dto.request.UserRequest;

import az.baku.divfinalproject.dto.response.UserResponse;

public interface UserService extends CrudService<UserRequest, UserResponse> {
    UserResponse register(RegisterRequest registerRequest);
    UserResponse getUserByEmail(String email);
    UserResponse getUserByPhoneNumber(String phoneNumber);
    UserResponse getUserByEmailAndPassword(String email, String password);
    UserResponse getUserByPhoneNumberAndPassword(String phoneNumber, String password);
}
