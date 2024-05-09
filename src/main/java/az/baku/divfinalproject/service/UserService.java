package az.baku.divfinalproject.service;

import az.baku.divfinalproject.dto.request.LoginRequest;
import az.baku.divfinalproject.dto.request.RegisterRequest;
import az.baku.divfinalproject.dto.request.Request;
import az.baku.divfinalproject.dto.request.UserRequest;

import az.baku.divfinalproject.dto.response.UserResponse;
import az.baku.divfinalproject.entity.User;
import org.springframework.http.ResponseEntity;

public interface UserService extends CrudService<UserRequest, UserResponse> {
    UserResponse createWithEmail(RegisterRequest registerRequest);
    UserResponse createWithPhoneNumber(RegisterRequest registerRequest);
    UserResponse getUserByEmail(String email);
    UserResponse getUserByPhoneNumber(String phoneNumber);
    UserResponse getUserByEmailAndPassword(String email, String password);
    UserResponse getUserByPhoneNumberAndPassword(String phoneNumber, String password);
    ResponseEntity<?> deleteUserByAdmin(Request<UserRequest> request);
    ResponseEntity<?> blockUserByAdmin(Request<UserRequest> request);
    ResponseEntity<?> cancelUserSubscriptionByAdmin(Request<UserRequest> request);
    ResponseEntity<?> updateUserByAdmin(Request<RegisterRequest> request);
    ResponseEntity<?> activateUserByAdmin(Request<UserRequest> request);
    ResponseEntity<?> authenticateUser(LoginRequest loginRequest);
    ResponseEntity<?> registerUserWithEmail(RegisterRequest userRequest);
    ResponseEntity<?> registerWithPhoneNumber(RegisterRequest signUpRequest);



}
