package az.baku.divfinalproject.service.impl;

import az.baku.divfinalproject.dto.request.RoleRequest;
import az.baku.divfinalproject.dto.response.RoleResponse;
import az.baku.divfinalproject.entity.Role;
import az.baku.divfinalproject.mapper.RoleMapper;
import az.baku.divfinalproject.repository.RoleRepository;
import az.baku.divfinalproject.service.CrudService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements CrudService<RoleRequest, RoleResponse> {
    private static final Logger log = LoggerFactory.getLogger(RoleServiceImpl.class);
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    public RoleResponse create(RoleRequest request) {
        Role role = roleMapper.toEntity(request);
        roleRepository.save(role);
        return roleMapper.toResponse(role);
    }

    @Override
    public RoleResponse update(long id, RoleRequest request) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new RuntimeException("Role not found with id: " + id));
        return roleMapper.toResponse(roleRepository.save(roleMapper.partialUpdate(request,role)));
    }

    @Override
    public void delete(long id) {
        roleRepository.deleteById(id);
    }

    @Override
    public RoleResponse getById(long id) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new RuntimeException("Role not found with id: " + id));
        return roleMapper.toResponse(role);
    }

    @Override
    public Collection<RoleResponse> findAll() {
        List<Role> all = roleRepository.findAll();
        return all.stream().map(roleMapper::toResponse).collect(Collectors.toList());
    }

    public Role findByName(String name) {
        Optional<Role> byName = roleRepository.findByName(name);
        return byName.orElse(null);
    }

    public RoleResponse getRoleByName(String name) {
        Role role = findByName(name);
        if (role != null) {
            return roleMapper.toResponse(role);
        } else {
            log.error("Role not found with name: {}" , name);
            throw new RuntimeException("Role not found with name: " + name);
        }
    }
}
