package az.baku.divfinalproject.mapper;

import az.baku.divfinalproject.dto.request.PropertyTypeRequest;
import az.baku.divfinalproject.dto.response.PropertyTypeResponse;
import az.baku.divfinalproject.entity.PropertyType;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface PropertyTypeMapper {
    PropertyType toEntity(PropertyTypeRequest propertyTypeRequest);

    PropertyTypeResponse toResponse(PropertyType propertyType);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    PropertyType partialUpdate(PropertyTypeRequest propertyTypeRequest, @MappingTarget PropertyType propertyType);
}