package az.baku.divfinalproject.controller;

import az.baku.divfinalproject.dto.request.AdvertDetailsRequest;
import az.baku.divfinalproject.dto.request.Request;
import az.baku.divfinalproject.dto.response.AdvertDetailsResponse;
import az.baku.divfinalproject.entity.AdvertDetails;
import az.baku.divfinalproject.service.impl.AdvertDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/advert-details")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AdvertDetailsController {
    private final AdvertDetailsServiceImpl advertDetailsService;

    @GetMapping("/advert-details")
    public AdvertDetailsResponse getAdvertDetailsById(@RequestBody Request<AdvertDetailsRequest> request) {
        return advertDetailsService.getById(request.getId());
    }

    @GetMapping("/get-all")
    public Collection<AdvertDetailsResponse> getAllAdvertDetails() {
        return advertDetailsService.findAll();
    }

    @PostMapping("/create")
    public AdvertDetailsResponse createAdvertDetails(@RequestBody AdvertDetailsRequest request) {
        return advertDetailsService.create(request);
    }

    @PutMapping("/advert-details")
    public AdvertDetailsResponse updateAdvertDetails(@RequestBody Request<AdvertDetailsRequest> request) {
        return advertDetailsService.update(request.getId(), request.getRequest());
    }

    @DeleteMapping("/advert-details")
    public void deleteAdvertDetails(@RequestBody Request<AdvertDetailsRequest> request) {
        advertDetailsService.delete(request.getId());
    }

    @GetMapping("/searching-count/{searchingCount}")
    public List<AdvertDetailsResponse> getAdvertDetailsBySearchingCount(@PathVariable int searchingCount) {
        return advertDetailsService.findBySearchingCount(searchingCount);
    }

    @GetMapping("/living-count/{livingCount}")
    public List<AdvertDetailsResponse> getAdvertDetailsByLivingCount(@PathVariable int livingCount) {
        return advertDetailsService.findByLivingCount(livingCount);
    }

    @GetMapping("/gender/{gender}")
    public List<AdvertDetailsResponse> getAdvertDetailsByGender(@PathVariable String gender) {
        return advertDetailsService.findByGender(gender);
    }
}
