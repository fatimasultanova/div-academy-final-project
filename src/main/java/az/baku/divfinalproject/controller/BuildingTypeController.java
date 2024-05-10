package az.baku.divfinalproject.controller;

import az.baku.divfinalproject.dto.request.BuildingTypeRequest;
import az.baku.divfinalproject.dto.request.Request;
import az.baku.divfinalproject.dto.response.BuildingTypeResponse;
import az.baku.divfinalproject.service.impl.BuildingTypeServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/building-type")
@CrossOrigin(origins = "*", maxAge = 3600)
public class BuildingTypeController {
    private final BuildingTypeServiceImpl buildingTypeService;

    @PostMapping("/create")
    public BuildingTypeResponse createBuildingType(@RequestBody BuildingTypeRequest request) {
        return buildingTypeService.create(request);
    }

    @PostMapping("/update")
    public BuildingTypeResponse updateBuildingType(@RequestBody Request<BuildingTypeRequest> request) {
        return buildingTypeService.update(request.getId(), request.getRequest());
    }

    @GetMapping("/get-building-type")
    public BuildingTypeResponse getBuildingTypeById(@RequestBody Request<BuildingTypeRequest> request) {
        return buildingTypeService.getById(request.getId());
    }

    @DeleteMapping("/delete")
    public void deleteBuildingType(@RequestBody Request<BuildingTypeRequest> request) {
        buildingTypeService.delete(request.getId());
    }

    @GetMapping("/all-building-types")
    public Collection<BuildingTypeResponse> getAllBuildingTypes() {
        return buildingTypeService.findAll();
    }

    @GetMapping("/type/{type}")
    public BuildingTypeResponse getBuildingTypeByType(@PathVariable("type") String type) {
       return buildingTypeService.getBuildingTypeByType(type);
    }
}
