package az.baku.divfinalproject.mapper;

import az.baku.divfinalproject.dto.request.PropertyDetailsRequest;
import az.baku.divfinalproject.dto.response.PropertyDetailsResponse;
import az.baku.divfinalproject.entity.PropertyDetails;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface PropertyDetailsMapper {
    PropertyDetails toEntity(PropertyDetailsRequest propertyDetailsRequest);

    PropertyDetailsResponse toResponse(PropertyDetails propertyDetails);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE , nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    PropertyDetails partialUpdate(PropertyDetailsRequest propertyDetailsRequest,@MappingTarget PropertyDetails propertyDetails);
}