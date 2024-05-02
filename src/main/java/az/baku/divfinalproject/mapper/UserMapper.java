package az.baku.divfinalproject.mapper;

import az.baku.divfinalproject.dto.request.UserRequest;
import az.baku.divfinalproject.dto.response.UserResponse;
import az.baku.divfinalproject.entity.Role;
import az.baku.divfinalproject.entity.User;
import org.mapstruct.*;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UserMapper {
    User toEntity(UserRequest userRequest);


    UserResponse toResponse(User user);

    default Set<String> mapRoles(Set<Role> roles) {
        return roles.stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User partialUpdate(@MappingTarget User user, UserRequest userRequest);
}