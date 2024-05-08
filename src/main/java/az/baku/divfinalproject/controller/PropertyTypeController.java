package az.baku.divfinalproject.controller;

import az.baku.divfinalproject.dto.request.PropertyTypeRequest;
import az.baku.divfinalproject.dto.request.Request;
import az.baku.divfinalproject.dto.response.PropertyTypeResponse;
import az.baku.divfinalproject.entity.PropertyType;
import az.baku.divfinalproject.mapper.PropertyTypeMapper;
import az.baku.divfinalproject.service.impl.PropertyTypeServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/property-type")
@RequiredArgsConstructor
public class PropertyTypeController {
    private final PropertyTypeServiceImpl propertyTypeService;

    private final PropertyTypeMapper propertyTypeMapper;

    @PostMapping("/create")
    public PropertyTypeResponse createPropertyType(@RequestBody PropertyTypeRequest request) {
        return propertyTypeService.create(request);
    }

    @PostMapping("/update")
    public PropertyTypeResponse updatePropertyType(@RequestBody Request<PropertyTypeRequest> request) {
        return propertyTypeService.update(request.getId(), request.getRequest());
    }

    @GetMapping("/get-property-type")
    public PropertyTypeResponse getPropertyTypeById(@RequestBody Request<PropertyTypeRequest> request) {
        return propertyTypeService.getById(request.getId());
    }

    @DeleteMapping("/delete")
    public void deletePropertyType(@RequestBody Request<PropertyTypeRequest> request) {
        propertyTypeService.delete(request.getId());
    }

    @GetMapping("/all-property-types")
    public Collection<PropertyTypeResponse> getAllPropertyTypes() {
        return propertyTypeService.findAll();
    }

    @GetMapping("/type/{type}")
    public PropertyTypeResponse getPropertyTypeByType(@PathVariable("type") String type) {
        PropertyType PropertyType = propertyTypeService.findByType(type);
        if (PropertyType != null) {
            return propertyTypeMapper.toResponse(PropertyType);
        } else {
            return null;
        }
    }
}
