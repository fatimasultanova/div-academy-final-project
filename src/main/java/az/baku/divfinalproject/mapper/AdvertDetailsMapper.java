package az.baku.divfinalproject.mapper;

import az.baku.divfinalproject.dto.request.AdvertDetailsRequest;
import az.baku.divfinalproject.dto.response.AdvertDetailsResponse;
import az.baku.divfinalproject.entity.AdvertDetails;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface AdvertDetailsMapper {
    AdvertDetails toEntity(AdvertDetailsRequest advertDetailsRequest);

    AdvertDetailsResponse toResponse(AdvertDetails advertDetails);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE , nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    AdvertDetails partialUpdate(AdvertDetailsRequest advertDetailsRequest,@MappingTarget AdvertDetails advertDetails);
}