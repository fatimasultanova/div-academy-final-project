package az.baku.divfinalproject.controller;

import az.baku.divfinalproject.dto.request.PaymentRequest;
import az.baku.divfinalproject.service.impl.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payment")
public class PaymentController {
   private final PaymentService paymentService;

    @PostMapping("/subscription-payment")
    public ResponseEntity<String> createPayment(@RequestBody PaymentRequest paymentRequest) {
        return paymentService.createPayment(paymentRequest);
    }
}