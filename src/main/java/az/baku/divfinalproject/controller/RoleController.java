package az.baku.divfinalproject.controller;

import az.baku.divfinalproject.dto.request.RoleRequest;
import az.baku.divfinalproject.dto.request.Request;
import az.baku.divfinalproject.dto.response.RoleResponse;
import az.baku.divfinalproject.service.impl.RoleServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/role")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class RoleController {
    private final RoleServiceImpl roleService;

    @PostMapping()
    public RoleResponse createRole(@RequestBody RoleRequest request) {
        return roleService.create(request);
    }

    @PutMapping()
    public RoleResponse updateRole(@RequestBody Request<RoleRequest> request) {
        return roleService.update(request.getId(), request.getRequest());
    }

    @GetMapping()
    public RoleResponse getRoleById(@RequestBody Request<RoleRequest> request) {
        return roleService.getById(request.getId());
    }

    @DeleteMapping()
    public void deleteRole(@RequestBody Request<RoleRequest> request) {
        roleService.delete(request.getId());
    }

    @GetMapping("/all-roles")
    public Collection<RoleResponse> getAllRoles() {
        return roleService.findAll();
    }

    @GetMapping("/role/{name}")
    public RoleResponse getRoleByName(@PathVariable("name") String name) {
        return roleService.getRoleByName(name);
    }
}