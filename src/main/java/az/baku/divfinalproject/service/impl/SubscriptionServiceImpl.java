package az.baku.divfinalproject.service.impl;

import az.baku.divfinalproject.dto.request.SubscriptionRequest;
import az.baku.divfinalproject.dto.response.ExceptionResponse;
import az.baku.divfinalproject.dto.response.SubscriptionResponse;
import az.baku.divfinalproject.entity.Subscription;
import az.baku.divfinalproject.exception.ApplicationException;
import az.baku.divfinalproject.exception.ExceptionEnums;
import az.baku.divfinalproject.mapper.SubscriptionMapper;
import az.baku.divfinalproject.repository.SubscriptionRepository;
import az.baku.divfinalproject.service.CrudService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
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
    private final Logger logger = LoggerFactory.getLogger(SubscriptionServiceImpl.class);


    @Override
    public SubscriptionResponse create(SubscriptionRequest request) {
        logger.info("Create Subscription process starting");
        Subscription subscription = subscriptionMapper.toEntity(request);
        subscriptionRepository.save(subscription);
        logger.info("Create Subscription process finished");
        return subscriptionMapper.toResponse(subscription);
    }

    @Override
    public SubscriptionResponse update(long id, SubscriptionRequest request) {
        logger.info("Update Subscription process starting for ID: {}", id);
        Subscription subscription = subscriptionRepository.findById(id).orElseThrow(() -> new ApplicationException(new ExceptionResponse(ExceptionEnums.SUBSCRIPTION_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND)));
        Subscription updatedSubscription = subscriptionRepository.save(subscriptionMapper.partialUpdate(request, subscription));
        logger.info("Update Subscription process finished for ID: {}", id);
        return subscriptionMapper.toResponse(updatedSubscription);
    }

    @Override
    public void delete(long id) {
        logger.info("Delete Subscription process starting for ID: {}", id);
        subscriptionRepository.deleteById(id);
        logger.info("Delete Subscription process finished for ID: {}", id);
    }

    @Override
    public SubscriptionResponse getById(long id) {
        logger.info("Get Subscription process starting for ID: {}", id);
        Subscription byId = subscriptionRepository.findById(id).orElseThrow(()-> new ApplicationException(new ExceptionResponse(ExceptionEnums.SUBSCRIPTION_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND)));
        logger.info("Get Subscription process finished for ID: {}", id);
        return subscriptionMapper.toResponse(byId);
    }

    @Override
    public Collection<SubscriptionResponse> findAll() {
        logger.info("Find all Subscriptions process starting");
        List<Subscription> all = subscriptionRepository.findAll();
        logger.info("Find all Subscriptions process finished");
        return all.stream().map(subscriptionMapper::toResponse).collect(Collectors.toList());
    }

    public SubscriptionResponse getByType(String type) {
        logger.info("Get Subscription by Type process starting for Type: {}", type);
        Subscription byType = subscriptionRepository.findByType(type);
        logger.info("Get Subscription by Type process finished for Type: {}", type);
        return subscriptionMapper.toResponse(byType);
    }

    public List<Subscription> getByAmountIsLessThan(long amount) {
        logger.info("Get Subscriptions by Amount Is Less Than process starting for Amount: {}", amount);
        List<Subscription> subscriptions = subscriptionRepository.findAllByAmountIsLessThan(amount);
        logger.info("Get Subscriptions by Amount Is Less Than process finished for Amount: {}", amount);
        return subscriptions;
    }

}
