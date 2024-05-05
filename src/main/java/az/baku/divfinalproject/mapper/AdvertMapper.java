package az.baku.divfinalproject.mapper;

import az.baku.divfinalproject.dto.request.AdvertRequest;
import az.baku.divfinalproject.dto.response.AdvertResponse;
import az.baku.divfinalproject.entity.*;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface AdvertMapper {
    Advert toEntity(AdvertRequest advertRequest);

    AdvertResponse toResponse(Advert advert);

    default AdvertType toAdvertType(String value) {
        return AdvertType.fromString(value);
    }

    default String toValue(AdvertType type) {
        return AdvertType.fromAdvertType(type);
    }

    default long toId(User user) {
        return user.getId();
    }

    default long toId(AdvertDetails advertDetails) {
        return advertDetails.getId();
    }

    default long toId(PropertyDetails propertyDetails) {
        return propertyDetails.getId();
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE , nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    Advert partialUpdate(AdvertRequest advertRequest,@MappingTarget Advert advert);
}