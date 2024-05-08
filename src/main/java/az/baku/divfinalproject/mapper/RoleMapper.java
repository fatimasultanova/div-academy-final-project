package az.baku.divfinalproject.mapper;

import az.baku.divfinalproject.dto.request.RoleRequest;
import az.baku.divfinalproject.dto.response.RoleResponse;
import az.baku.divfinalproject.entity.Role;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface RoleMapper {
    Role toEntity(RoleRequest roleRequest);

    RoleResponse toResponse(Role role);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Role partialUpdate(RoleRequest roleRequest, @MappingTarget Role role);
}