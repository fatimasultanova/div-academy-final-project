package az.baku.divfinalproject.controller;

import az.baku.divfinalproject.dto.request.PaymentRequest;
import az.baku.divfinalproject.service.impl.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payment")
@CrossOrigin(origins = "*", maxAge = 3600)
public class PaymentController {
   private final PaymentService paymentService;

    @PostMapping("/subscription-payment")
    public ResponseEntity<String> createPayment(@RequestBody PaymentRequest paymentRequest) {
        return paymentService.createPayment(paymentRequest);
    }
}