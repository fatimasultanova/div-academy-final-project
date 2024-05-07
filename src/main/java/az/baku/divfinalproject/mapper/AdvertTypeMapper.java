package az.baku.divfinalproject.mapper;

import az.baku.divfinalproject.dto.request.AdvertTypeRequest;
import az.baku.divfinalproject.dto.response.AdvertTypeResponse;
import az.baku.divfinalproject.entity.AdvertType;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface AdvertTypeMapper {
    AdvertType toEntity(AdvertTypeRequest advertTypeRequest);

    AdvertTypeResponse toResponse(AdvertType advertType);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    AdvertType partialUpdate(AdvertTypeRequest advertTypeRequest, @MappingTarget AdvertType advertType);
}
