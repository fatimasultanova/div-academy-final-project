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
public class AdvertDetailsController {
    private final AdvertDetailsServiceImpl advertDetailsService;

    @GetMapping("/get-advert-details")
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

    @PutMapping("/update")
    public AdvertDetailsResponse updateAdvertDetails(@RequestBody Request<AdvertDetailsRequest> request) {
        return advertDetailsService.update(request.getId(), request.getRequest());
    }

    @DeleteMapping("/delete")
    public void deleteAdvertDetails(@RequestBody Request<AdvertDetailsRequest> request) {
        advertDetailsService.delete(request.getId());
    }

    @GetMapping("/searching-count/{searchingCount}")
    public List<AdvertDetails> getAdvertDetailsBySearchingCount(@PathVariable int searchingCount) {
        return advertDetailsService.findBySearchingCount(searchingCount);
    }

    @GetMapping("/living-count/{livingCount}")
    public List<AdvertDetails> getAdvertDetailsByLivingCount(@PathVariable int livingCount) {
        return advertDetailsService.findByLivingCount(livingCount);
    }

    @GetMapping("/gender/{gender}")
    public List<AdvertDetails> getAdvertDetailsByGender(@PathVariable String gender) {
        return advertDetailsService.findByGender(gender);
    }
}
