package az.baku.divfinalproject.mapper;

import az.baku.divfinalproject.dto.request.UserRequest;
import az.baku.divfinalproject.dto.response.UserResponse;
import az.baku.divfinalproject.entity.Advert;
import az.baku.divfinalproject.entity.Role;
import az.baku.divfinalproject.entity.User;
import org.mapstruct.*;

import java.util.List;
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

    default List<Long> viewedAdverts(List<Advert> adverts) {
        return adverts.stream()
                .map(Advert::getId)
                .collect(Collectors.toList());
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE , nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    User partialUpdate(UserRequest userRequest,@MappingTarget User user);
}