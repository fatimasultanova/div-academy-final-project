package az.baku.divfinalproject.controller;

import az.baku.divfinalproject.dto.request.AdvertRequest;
import az.baku.divfinalproject.dto.request.Request;
import az.baku.divfinalproject.dto.response.AdvertResponse;
import az.baku.divfinalproject.dto.response.UserResponse;
import az.baku.divfinalproject.entity.Advert;
import az.baku.divfinalproject.service.AdvertService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/adverts")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class AdvertController {
    private final AdvertService advertService;


    @PostMapping("/advert")
    public AdvertResponse createAdvert(@RequestBody AdvertRequest request) {
        return advertService.create(request);
    }

    @GetMapping("/advert")
    public AdvertResponse getAdvertById(@RequestBody Request<AdvertRequest> request) {
        return advertService.getById(request.getId());
    }

    @GetMapping("/contact")
    public UserResponse getContactInfoById(@RequestBody Request<AdvertRequest> request) {
        return advertService.getContactInformation(request);
    }

    @GetMapping("/all-adverts")
    public Collection<AdvertResponse> getAllAdverts() {
        return advertService.findAll();
    }

    @GetMapping("/user")
    public List<AdvertResponse> getAdvertsByUserId(@RequestBody Request<AdvertRequest> request) {
        return advertService.getAdverts(request.getId());
    }

    @GetMapping("/amount/{amount}")
    public List<Advert> getAdvertsByAmountMonthlyLessThanOrEqual(@PathVariable double amount) {
        return advertService.findAllByAmountMonthlyIsLessThanEqual(amount);
    }

    @GetMapping("/type")
    public List<Advert> getAdvertsByAdvertTypeId(@RequestBody Request<AdvertRequest> request) {
        return advertService.findAllByAdvertTypeId(request.getId());
    }

    @PutMapping("/advert")
    public AdvertResponse updateAdvert(@RequestBody Request<AdvertRequest> request) {
        return advertService.update(request.getId(), request.getRequest());
    }

    @DeleteMapping("/advert")
    public void deleteAdvert(@RequestBody Request<AdvertRequest> request) {
        advertService.delete(request.getId());
    }
}