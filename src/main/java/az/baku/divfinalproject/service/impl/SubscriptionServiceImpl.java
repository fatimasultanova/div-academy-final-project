package az.baku.divfinalproject.service.impl;

import az.baku.divfinalproject.dto.request.SubscriptionRequest;
import az.baku.divfinalproject.dto.response.SubscriptionResponse;
import az.baku.divfinalproject.entity.Subscription;
import az.baku.divfinalproject.mapper.SubscriptionMapper;
import az.baku.divfinalproject.repository.SubscriptionRepository;
import az.baku.divfinalproject.service.CrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements CrudService<SubscriptionRequest, SubscriptionResponse> {
    private final SubscriptionMapper subscriptionMapper;
    private final SubscriptionRepository subscriptionRepository;


    @Override
    public SubscriptionResponse create(SubscriptionRequest request) {
        Subscription subscription = Subscription.builder()
                .type(request.getType())
                .description(request.getDescription())
                .amount(request.getAmount())
                .requestCount(request.getRequestCount())
                .build();
        subscriptionRepository.save(subscription);
        return subscriptionMapper.toResponse(subscription);
    }

    @Override
    public SubscriptionResponse update(long id, SubscriptionRequest request) {
        Optional<Subscription> subscription = subscriptionRepository.findById(id);
        if (subscription.isPresent()) {
            subscription.get().setType(request.getType());
            subscription.get().setDescription(request.getDescription());
            subscription.get().setAmount(request.getAmount());
            subscription.get().setRequestCount(request.getRequestCount());
            subscriptionRepository.save(subscription.get());
            return subscriptionMapper.toResponse(subscription.get());
        }
        return null;
    }

    @Override
    public void delete(long id) {
        subscriptionRepository.deleteById(id);
    }

    @Override
    public SubscriptionResponse getById(long id) {
        Optional<Subscription> byId = subscriptionRepository.findById(id);
        return subscriptionMapper.toResponse(byId.orElse(null));
    }

    @Override
    public Collection<SubscriptionResponse> findAll() {
        List<Subscription> all = subscriptionRepository.findAll();
        return all.stream().map(subscriptionMapper::toResponse).collect(Collectors.toList());
    }

    public SubscriptionResponse getByType(String type) {
        Subscription byType = subscriptionRepository.findByType(type);
        return subscriptionMapper.toResponse(byType);
    }
}
