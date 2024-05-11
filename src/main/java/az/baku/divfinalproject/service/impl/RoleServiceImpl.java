package az.baku.divfinalproject.service.impl;

import az.baku.divfinalproject.dto.request.RoleRequest;
import az.baku.divfinalproject.dto.response.ExceptionResponse;
import az.baku.divfinalproject.dto.response.RoleResponse;
import az.baku.divfinalproject.entity.Role;
import az.baku.divfinalproject.exception.ApplicationException;
import az.baku.divfinalproject.exception.ExceptionEnums;
import az.baku.divfinalproject.mapper.RoleMapper;
import az.baku.divfinalproject.repository.RoleRepository;
import az.baku.divfinalproject.service.CrudService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements CrudService<RoleRequest, RoleResponse> {
    private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    public RoleResponse create(RoleRequest request) {
        logger.info("Create Role process starting");
        Role role = roleMapper.toEntity(request);
        roleRepository.save(role);
        logger.info("Create Role process finished");
        return roleMapper.toResponse(role);
    }

    @Override
    public RoleResponse update(long id, RoleRequest request) {
        logger.info("Update Role process starting for ID: {}", id);
        Role role = roleRepository.findById(id).orElseThrow(() -> new ApplicationException(new ExceptionResponse(ExceptionEnums.ROLE_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND)));
        Role updatedRole = roleRepository.save(roleMapper.partialUpdate(request, role));
        logger.info("Update Role process finished for ID: {}", id);
        return roleMapper.toResponse(updatedRole);
    }

    @Override
    public void delete(long id) {
        logger.info("Delete Role process starting for ID: {}", id);
        roleRepository.deleteById(id);
        logger.info("Delete Role process finished for ID: {}", id);
    }

    @Override
    public RoleResponse getById(long id) {
        logger.info("Get Role process starting for ID: {}", id);
        Role role = roleRepository.findById(id).orElseThrow(() -> new ApplicationException(new ExceptionResponse(ExceptionEnums.ROLE_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND)));
        logger.info("Get Role process finished for ID: {}", id);
        return roleMapper.toResponse(role);
    }

    @Override
    public Collection<RoleResponse> findAll() {
        logger.info("Find all Roles process starting");
        List<Role> all = roleRepository.findAll();
        logger.info("Find all Roles process finished");
        return all.stream().map(roleMapper::toResponse).collect(Collectors.toList());
    }

    public Role findByName(String name) {
        logger.info("Find Role by Name process starting for Name: {}", name);
        Optional<Role> byName = roleRepository.findByName(name);
        Role role = byName.orElse(null);
        logger.info("Find Role by Name process finished for Name: {}", name);
        return role;
    }

    public RoleResponse getRoleByName(String name) {
        logger.info("Get Role by Name process starting for Name: {}", name);
        Role role = findByName(name);
        if (role != null) {
            logger.info("Role found with name: {}", name);
            return roleMapper.toResponse(role);
        } else {
            logger.error("Role not found with name: {}", name);
            throw new ApplicationException(new ExceptionResponse(ExceptionEnums.ROLE_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND));
        }
    }

}
