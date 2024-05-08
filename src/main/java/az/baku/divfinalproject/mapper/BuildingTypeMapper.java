package az.baku.divfinalproject.mapper;

import az.baku.divfinalproject.dto.request.BuildingTypeRequest;
import az.baku.divfinalproject.dto.response.BuildingTypeResponse;
import az.baku.divfinalproject.entity.BuildingType;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface BuildingTypeMapper {
    BuildingType toEntity(BuildingTypeRequest buildingTypeRequest);

    BuildingTypeResponse toResponse(BuildingType buildingType);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    BuildingType partialUpdate(BuildingTypeRequest buildingTypeRequest, @MappingTarget BuildingType buildingType);
}