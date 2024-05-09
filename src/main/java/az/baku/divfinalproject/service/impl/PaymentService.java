package az.baku.divfinalproject.service.impl;

import az.baku.divfinalproject.dto.request.PaymentRequest;
import az.baku.divfinalproject.dto.response.ExceptionResponse;
import az.baku.divfinalproject.entity.Subscription;
import az.baku.divfinalproject.entity.User;
import az.baku.divfinalproject.entity.UsersSubscriptionsCounting;
import az.baku.divfinalproject.exception.ApplicationException;
import az.baku.divfinalproject.exception.ExceptionEnums;
import az.baku.divfinalproject.repository.SubscriptionRepository;
import az.baku.divfinalproject.repository.UserRepository;
import az.baku.divfinalproject.repository.UsersSubscriptionsCountingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);
    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final UsersSubscriptionsCountingRepository countingRepository;


    public ResponseEntity<String> createPayment(PaymentRequest paymentRequest) {
        String stripeEndpoint = "https://api.stripe.com/v1/payment_intents";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + paymentRequest.getSecretKey());
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String requestBody = "amount=" + paymentRequest.getAmount() +
                "&currency=" + paymentRequest.getCurrency() +
                "&payment_method=" + paymentRequest.getPayment_method();

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(stripeEndpoint, request, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            activateUserSubscription(paymentRequest.getUserId(), paymentRequest.getAmount());
            logger.info("Subscription activated for user {}" ,paymentRequest.getUserId());
            Optional<User> user = userRepository.findById(paymentRequest.getUserId());
            if (user.isPresent()) {
                Optional<UsersSubscriptionsCounting> byUser = countingRepository.findByUser(user.get());
                if (byUser.isPresent()) {
                    Subscription subscription = subscriptionRepository.findByAmount(paymentRequest.getAmount());
                    byUser.get().setCount(subscription.getRequestCount());
                    countingRepository.save(byUser.get());
                } else {
                    UsersSubscriptionsCounting counting = new UsersSubscriptionsCounting();
                    counting.setUser(user.get());
                    Subscription subscription = subscriptionRepository.findByAmount(paymentRequest.getAmount());
                    counting.setCount(subscription.getRequestCount());
                    countingRepository.save(counting);
                }
                logger.info("Payment processed successfully for user id: {}", paymentRequest.getUserId());
                return ResponseEntity.ok("Payment processed successfully"+ "\nUser id: " + paymentRequest.getUserId());
            }else {
                logger.warn("User not found: {}", paymentRequest.getUserId());
                throw new ApplicationException(new ExceptionResponse(ExceptionEnums.USER_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND));

            }
        }else {
            logger.error("Payment processing failed with status code: {}", response.getStatusCode());
            throw new ApplicationException(new ExceptionResponse(ExceptionEnums.PAYMENT_FAILED.getMessage(), HttpStatus.BAD_REQUEST));
        }
    }


    private void activateUserSubscription(long userId, long amount) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            Subscription subscription = subscriptionRepository.findByAmount(amount);
            if (subscription != null) {
                user.get().setSubscription(subscription);
                userRepository.save(user.get());
            }else {
                logger.error("Subscription not found with amount : {}", amount);
                throw new ApplicationException(new ExceptionResponse(ExceptionEnums.SUBSCRIPTION_FAILED.getMessage(),HttpStatus.NOT_FOUND));
            }
        }else {
            logger.error("User not found: {}", userId);
            throw new ApplicationException(new ExceptionResponse(ExceptionEnums.USER_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND));
        }
    }

}