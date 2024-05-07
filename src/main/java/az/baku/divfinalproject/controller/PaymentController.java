package az.baku.divfinalproject.controller;

import az.baku.divfinalproject.dto.request.PaymentRequest;
import az.baku.divfinalproject.entity.Subscription;
import az.baku.divfinalproject.entity.User;
import az.baku.divfinalproject.entity.UsersSubscriptionsCounting;
import az.baku.divfinalproject.repository.SubscriptionRepository;
import az.baku.divfinalproject.repository.UserRepository;
import az.baku.divfinalproject.repository.UsersSubscriptionsCountingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payment")
public class PaymentController {
    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final UsersSubscriptionsCountingRepository countingRepository;

    @PostMapping("/subscription-payment")
    public ResponseEntity<String> createPayment(@RequestBody PaymentRequest paymentRequest) {
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
            activateUserSubscription(paymentRequest.getUserId(),paymentRequest.getAmount());
            Optional<User> user = userRepository.findById(paymentRequest.getUserId());
            Optional<UsersSubscriptionsCounting> byUser = countingRepository.findByUser(user.get());
            if (byUser.isPresent()) {
                Subscription subscription = subscriptionRepository.findByAmount(paymentRequest.getAmount());
                byUser.get().setCount(subscription.getRequestCount());
                countingRepository.save(byUser.get());
            }else {
                UsersSubscriptionsCounting counting = new UsersSubscriptionsCounting();
                counting.setUser(user.get());
                Subscription subscription = subscriptionRepository.findByAmount(paymentRequest.getAmount());
                counting.setCount(subscription.getRequestCount());
                countingRepository.save(counting);
            }
            return ResponseEntity.ok("Payment processed successfully"+ "\nUser id: " + paymentRequest.getUserId());
        } else {
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        }
    }


    private void activateUserSubscription(long userId, long amount) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            Subscription subscription = subscriptionRepository.findByAmount(amount);
            user.get().setSubscription(subscription);
            userRepository.save(user.get());
        }
    }
}
