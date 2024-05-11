package az.baku.divfinalproject.controller;

import az.baku.divfinalproject.dto.request.Request;
import az.baku.divfinalproject.dto.request.SubscriptionRequest;
import az.baku.divfinalproject.dto.response.SubscriptionResponse;
import az.baku.divfinalproject.service.impl.SubscriptionServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/subscription")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class SubscriptionController {
    private final SubscriptionServiceImpl subscriptionService;

    @PostMapping()
    public SubscriptionResponse createRole(@RequestBody SubscriptionRequest request) {
        return subscriptionService.create(request);
    }

    @PutMapping()
    public SubscriptionResponse updateRole(@RequestBody Request<SubscriptionRequest> request) {
        return subscriptionService.update(request.getId(), request.getRequest());
    }

    @GetMapping()
    public SubscriptionResponse getSubsById(@RequestBody Request<SubscriptionRequest> request) {
        return subscriptionService.getById(request.getId());
    }

    @DeleteMapping()
    public void deleteRole(@RequestBody Request<SubscriptionRequest> request) {
        subscriptionService.delete(request.getId());
    }

    @GetMapping("/all-subscriptions")
    public Collection<SubscriptionResponse> getAllSubscriptions() {
        return subscriptionService.findAll();
    }

    @GetMapping("/type/{type}")
    public SubscriptionResponse getSubsByType(@PathVariable("type") String type) {
        return subscriptionService.getByType(type);
    }
}
