package az.baku.divfinalproject.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum ExceptionEnums {
    USER_NOT_FOUND("User not found"),
    ACCESS_DENIED("----------Access denied-----------"),
    ADVERT_NOT_FOUND("Advert not found"),
    USER_REGISTERED("User registered"),
    USER_UPDATED("User updated"),
    USER_DELETED("User deleted"),
    TOKEN_EXPIRED("Token expired"),
    VERIFICATION_FAILED("Verification failed"),
    VERIFICATION_SUCCEEDED("Verification succeeded"),
    VERIFICATION_CANCELLED("Verification cancelled"),
    TOKEN_NOT_FOUND("Token not found"),
    PASSWORD_INCORRECT("Password incorrect"),
    PAYMENT_FAILED("Payment failed"),
    PAYMENT_SUCCEEDED("Payment succeeded"),
    SUBMIT_FAILED("Submit failed"),
    SUBSCRIPTION_FAILED("Subscription failed"),
    SUBSCRIPTION_SUCCEEDED("Subscription succeeded"),
    SUBSCRIPTION_CANCELLED("Subscription cancelled"),
    ACCOUNT_NOT_FOUND("Account not found"),
    AUTHENTICATION_FAILED("Authentication failed");


    private final String message;

    ExceptionEnums(String message) {
        this.message = message;
    }
}