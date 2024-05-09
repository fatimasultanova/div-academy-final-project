package az.baku.divfinalproject.service;

import az.baku.divfinalproject.dto.request.*;

import az.baku.divfinalproject.dto.response.UserResponse;
import az.baku.divfinalproject.entity.User;
import org.springframework.http.ResponseEntity;

public interface UserService extends CrudService<UserRequest, UserResponse> {
    UserResponse createWithEmail(RegisterRequest registerRequest);
    UserResponse createWithPhoneNumber(RegisterRequest registerRequest);
    UserResponse getUserByEmail(String email);
    UserResponse getUserByPhoneNumber(String phoneNumber);
    ResponseEntity<?> deleteUserByAdmin(Request<UserRequest> request);
    ResponseEntity<?> blockUserByAdmin(Request<UserRequest> request);
    ResponseEntity<?> cancelUserSubscriptionByAdmin(Request<UserRequest> request);
    ResponseEntity<?> updateUserByAdmin(Request<RegisterRequest> request);
    ResponseEntity<?> activateUserByAdmin(Request<UserRequest> request);
    ResponseEntity<?> authenticateUser(LoginRequest loginRequest);
    ResponseEntity<?> registerUserWithEmail(RegisterRequest userRequest);
    ResponseEntity<?> registerWithPhoneNumber(RegisterRequest signUpRequest);
    ResponseEntity<?> getContactInformation(Request<AdvertRequest> request);
    ResponseEntity<?> updateEmail(Request<LoginRequest> updateRequest);
    ResponseEntity<?> updatePhone(Request<LoginRequest> updateRequest);
    ResponseEntity<?> updatePassword(Request<PasswordRequest> request);
    ResponseEntity<?> setUserAdmin(Request<LoginRequest> request);



}
