package az.baku.divfinalproject.controller;

import az.baku.divfinalproject.dto.request.PropertyTypeRequest;
import az.baku.divfinalproject.dto.request.Request;
import az.baku.divfinalproject.dto.response.PropertyTypeResponse;
import az.baku.divfinalproject.service.impl.PropertyTypeServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/property-type")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class PropertyTypeController {
    private final PropertyTypeServiceImpl propertyTypeService;

    @PostMapping()
    public PropertyTypeResponse createPropertyType(@RequestBody PropertyTypeRequest request) {
        return propertyTypeService.create(request);
    }

    @PutMapping()
    public PropertyTypeResponse updatePropertyType(@RequestBody Request<PropertyTypeRequest> request) {
        return propertyTypeService.update(request.getId(), request.getRequest());
    }

    @GetMapping()
    public PropertyTypeResponse getPropertyTypeById(@RequestBody Request<PropertyTypeRequest> request) {
        return propertyTypeService.getById(request.getId());
    }

    @DeleteMapping()
    public void deletePropertyType(@RequestBody Request<PropertyTypeRequest> request) {
        propertyTypeService.delete(request.getId());
    }

    @GetMapping("/all-property-types")
    public Collection<PropertyTypeResponse> getAllPropertyTypes() {
        return propertyTypeService.findAll();
    }

    @GetMapping("/type/{type}")
    public PropertyTypeResponse getPropertyTypeByType(@PathVariable("type") String type) {
        return propertyTypeService.getPropertyTypeByType(type);
    }
}
