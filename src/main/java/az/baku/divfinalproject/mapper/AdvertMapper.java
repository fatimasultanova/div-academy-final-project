package az.baku.divfinalproject.mapper;

import az.baku.divfinalproject.dto.request.AdvertRequest;
import az.baku.divfinalproject.dto.response.AdvertResponse;
import az.baku.divfinalproject.entity.Advert;
import az.baku.divfinalproject.entity.AdvertType;
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

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE , nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    Advert partialUpdate(AdvertRequest advertRequest,@MappingTarget Advert advert);
}