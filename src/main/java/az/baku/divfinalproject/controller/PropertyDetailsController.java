package az.baku.divfinalproject.controller;

import az.baku.divfinalproject.dto.request.PropertyDetailsRequest;
import az.baku.divfinalproject.dto.request.Request;
import az.baku.divfinalproject.dto.response.PropertyDetailsResponse;
import az.baku.divfinalproject.entity.PropertyDetails;
import az.baku.divfinalproject.service.impl.PropertyDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/property-details")
@RequiredArgsConstructor
public class PropertyDetailsController {

    private final PropertyDetailsServiceImpl propertyDetailsService;


    @PostMapping("/create")
    public PropertyDetailsResponse createPropertyDetails(@RequestBody PropertyDetailsRequest request) {
        return propertyDetailsService.create(request);
    }

    @GetMapping("/get-details")
    public PropertyDetailsResponse getPropertyDetailsById(@RequestBody Request<PropertyDetailsRequest> request) {
        return propertyDetailsService.getById(request.getId());
    }

    @GetMapping("/all")
    public Collection<PropertyDetailsResponse> getAllPropertyDetails() {
        return propertyDetailsService.findAll();
    }

    @PutMapping("/update")
    public PropertyDetailsResponse updatePropertyDetails(@RequestBody Request<PropertyDetailsRequest> request) {
        return propertyDetailsService.update(request.getId(), request.getRequest());
    }

    @DeleteMapping("/delete")
    public void deletePropertyDetails(@RequestBody Request<PropertyDetailsRequest> request) {
        propertyDetailsService.delete(request.getId());
    }

    @GetMapping("/number-floors/{numberFloors}")
    public List<PropertyDetails> getPropertyDetailsByNumberFloors(@PathVariable int numberFloors) {
        return propertyDetailsService.findByNumberFloors(numberFloors);
    }

    @GetMapping("/room-floor/{roomFloor}")
    public List<PropertyDetails> getPropertyDetailsByRoomFloor(@PathVariable int roomFloor) {
        return propertyDetailsService.findByRoomFloor(roomFloor);
    }

    @GetMapping("/gas/{isGas}")
    public List<PropertyDetails> getPropertyDetailsByGas(@PathVariable boolean isGas) {
        return propertyDetailsService.findByGas(isGas);
    }

    @GetMapping("/square-m/{square_m}")
    public List<PropertyDetails> getPropertyDetailsBySquareM(@PathVariable long square_m) {
        return propertyDetailsService.findBySquareM(square_m);
    }

    @GetMapping("/address/{address}")
    public List<PropertyDetails> getPropertyDetailsByAddress(@PathVariable String address) {
        return propertyDetailsService.findByAddress(address);
    }

    @GetMapping("/elevator/{elevator}")
    public List<PropertyDetails> getPropertyDetailsByElevator(@PathVariable boolean elevator) {
        return propertyDetailsService.findByElevator(elevator);
    }
}
