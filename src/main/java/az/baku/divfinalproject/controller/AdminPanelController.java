package az.baku.divfinalproject.controller;

import az.baku.divfinalproject.dto.request.*;
import az.baku.divfinalproject.dto.response.AdvertResponse;
import az.baku.divfinalproject.dto.response.UserResponse;
import az.baku.divfinalproject.entity.Subscription;
import az.baku.divfinalproject.service.AdvertService;
import az.baku.divfinalproject.service.UserService;
import az.baku.divfinalproject.service.impl.AdvertTypeServiceImpl;
import az.baku.divfinalproject.service.impl.BuildingTypeServiceImpl;
import az.baku.divfinalproject.service.impl.PropertyTypeServiceImpl;
import az.baku.divfinalproject.service.impl.SubscriptionServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/admin-panel")
@RequiredArgsConstructor
@Secured("ROLE_ADMIN")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AdminPanelController {
    private final UserService userService;
    private final AdvertService advertService;
    private final AdvertTypeServiceImpl advertTypeService;
    private final BuildingTypeServiceImpl buildingTypeService;
    private final PropertyTypeServiceImpl propertyTypeService;
    private final SubscriptionServiceImpl subscriptionService;

    @GetMapping("/all-users")
    //@PreAuthorize()
    public ResponseEntity<?> getAllUsers() {
        Collection<UserResponse> all = userService.findAll();
        if (all.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(all);
    }

    @PatchMapping("/user")
    public ResponseEntity<?> activateUserPhone(@RequestBody Request<UserRequest> request) {
        return userService.activateUserByAdmin(request);
    }

    @PutMapping("/user")
    public ResponseEntity<?> update(@Valid @RequestBody Request<RegisterRequest> request) {
       return userService.updateUserByAdmin(request);
    }

    @PutMapping("/block-user")
    public ResponseEntity<?> blockUser(@Valid @RequestBody Request<UserRequest> request) {
        return userService.blockUserByAdmin(request);
    }

    @DeleteMapping("/user")
    public ResponseEntity<?> deleteUser(@Valid @RequestBody Request<UserRequest> request) {
       return userService.deleteUserByAdmin(request);
    }

    @PatchMapping("/user-subscription")
    public ResponseEntity<?> cancelUserSubscription(@Valid @RequestBody Request<UserRequest> request) {
        return userService.cancelUserSubscriptionByAdmin(request);
    }

    @GetMapping("/adverts")
    public ResponseEntity<?> getAllAdverts() {
        Collection<AdvertResponse> all = advertService.findAll();
        return all.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(all);
    }

    @PutMapping("/advert")
    public ResponseEntity<?> updateAdvert(@Valid @RequestBody Request<AdvertRequest> request) {
        return ResponseEntity.ok(advertService.update(request.getId(), request.getRequest()));
    }

    @DeleteMapping("/advert")
    public ResponseEntity<?> deleteAdvert(@Valid @RequestBody Request<AdvertRequest> request) {
        advertService.delete(request.getId());
        return ResponseEntity.ok("Advert deleted! ");
    }

    @PostMapping("/advert-type")
    public ResponseEntity<?> addAdvertType(@Valid @RequestBody Request<AdvertTypeRequest> request) {
        return ResponseEntity.ok(advertTypeService.create(request.getRequest()));
    }

    @PostMapping("/building-type")
    public ResponseEntity<?> addBuildingType(@Valid @RequestBody Request<BuildingTypeRequest> request) {
        return ResponseEntity.ok(buildingTypeService.create(request.getRequest()));
    }

    @PostMapping("/property-type")
    public ResponseEntity<?> addPropertyType(@Valid @RequestBody Request<PropertyTypeRequest> request) {
        return ResponseEntity.ok(propertyTypeService.create(request.getRequest()));
    }

    @PostMapping("/subscription")
    public ResponseEntity<?> addSubscription(@Valid @RequestBody Request<SubscriptionRequest> request) {
        return ResponseEntity.ok(subscriptionService.create(request.getRequest()));
    }

    @GetMapping("/subscriptions-by/{amount}")
    public List<Subscription> byAmountIsLessThan(@PathVariable long amount) {
        return subscriptionService.getByAmountIsLessThan(amount);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponse> getUserByEmail(@PathVariable String email) {
        UserResponse userResponse = userService.getUserByEmail(email);
        return ResponseEntity.ok(userResponse);
    }

    @GetMapping("/phone/{phoneNumber}")
    public ResponseEntity<UserResponse> getUserByPhoneNumber(@PathVariable String phoneNumber) {
        UserResponse userResponse = userService.getUserByPhoneNumber(phoneNumber);
        return ResponseEntity.ok(userResponse);
    }

    @PutMapping("/user-admin")
    public ResponseEntity<?> setUserAdmin(@Valid @RequestBody Request<LoginRequest> request) {
        return userService.setUserAdmin(request);
    }
}