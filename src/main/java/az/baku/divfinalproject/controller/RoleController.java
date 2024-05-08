package az.baku.divfinalproject.controller;

import az.baku.divfinalproject.dto.request.RoleRequest;
import az.baku.divfinalproject.dto.request.Request;
import az.baku.divfinalproject.dto.response.RoleResponse;
import az.baku.divfinalproject.entity.Role;
import az.baku.divfinalproject.mapper.RoleMapper;
import az.baku.divfinalproject.service.impl.RoleServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/role")
@RequiredArgsConstructor
public class RoleController {
    private final RoleServiceImpl roleService;
    private final RoleMapper roleMapper;

    @PostMapping("/create")
    public RoleResponse createRole(@RequestBody RoleRequest request) {
        return roleService.create(request);
    }

    @PostMapping("/update")
    public RoleResponse updateRole(@RequestBody Request<RoleRequest> request) {
        return roleService.update(request.getId(), request.getRequest());
    }

    @GetMapping("/get-role")
    public RoleResponse getRoleById(@RequestBody Request<RoleRequest> request) {
        return roleService.getById(request.getId());
    }

    @DeleteMapping("/delete")
    public void deleteRole(@RequestBody Request<RoleRequest> request) {
        roleService.delete(request.getId());
    }

    @GetMapping("/all-roles")
    public Collection<RoleResponse> getAllRoles() {
        return roleService.findAll();
    }

    @GetMapping("/role/{name}")
    public RoleResponse getRoleByName(@PathVariable("name") String name) {
        Role role = roleService.findByName(name);
        if (role != null) {
            return roleMapper.toResponse(role);
        } else {
            return null;
        }
    }

}
