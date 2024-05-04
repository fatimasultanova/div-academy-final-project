package az.baku.divfinalproject.mapper;

import az.baku.divfinalproject.dto.request.SubscriptionRequest;
import az.baku.divfinalproject.dto.response.SubscriptionResponse;
import az.baku.divfinalproject.entity.Subscription;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface SubscriptionMapper {
    Subscription toEntity(SubscriptionRequest subscriptionRequest);

    SubscriptionResponse toResponse(Subscription subscription);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Subscription partialUpdate(SubscriptionRequest subscriptionRequest, @MappingTarget Subscription subscription);
}