package az.baku.divfinalproject.controller;

import az.baku.divfinalproject.dto.request.AdvertTypeRequest;
import az.baku.divfinalproject.dto.request.Request;
import az.baku.divfinalproject.dto.response.AdvertTypeResponse;
import az.baku.divfinalproject.service.impl.AdvertTypeServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/advert-type")
@RequiredArgsConstructor
public class AdvertTypeController {
    private final AdvertTypeServiceImpl advertTypeService;

    @PostMapping("/create")
    public AdvertTypeResponse createAdvertType(@RequestBody AdvertTypeRequest request) {
        return advertTypeService.create(request);
    }

    @PostMapping("/update")
    public AdvertTypeResponse updateAdvertType(@RequestBody Request<AdvertTypeRequest> request) {
        return advertTypeService.update(request.getId(), request.getRequest());
    }

    @GetMapping("/get-advert-type")
    public AdvertTypeResponse getAdvertTypeById(@RequestBody Request<AdvertTypeRequest> request) {
        return advertTypeService.getById(request.getId());
    }

    @DeleteMapping("/delete")
    public void deleteAdvertType(@RequestBody Request<AdvertTypeRequest> request) {
        advertTypeService.delete(request.getId());
    }

    @GetMapping("/all-advert-types")
    public Collection<AdvertTypeResponse> getAllAdvertTypes() {
       return advertTypeService.findAll();
    }

    @GetMapping("/type/{type}")
    public AdvertTypeResponse getAdvertTypeByType(@PathVariable("type") String type) {
        return advertTypeService.getAdvertTypeByType(type);
    }
}
