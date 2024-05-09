package az.baku.divfinalproject.service.impl;

import az.baku.divfinalproject.dto.request.AdvertRequest;
import az.baku.divfinalproject.dto.request.Request;
import az.baku.divfinalproject.dto.response.AdvertResponse;
import az.baku.divfinalproject.dto.response.ExceptionResponse;
import az.baku.divfinalproject.dto.response.UserResponse;
import az.baku.divfinalproject.entity.Advert;
import az.baku.divfinalproject.entity.AdvertType;
import az.baku.divfinalproject.entity.Role;
import az.baku.divfinalproject.entity.User;
import az.baku.divfinalproject.exception.ApplicationException;
import az.baku.divfinalproject.exception.ExceptionEnums;
import az.baku.divfinalproject.mapper.AdvertMapper;
import az.baku.divfinalproject.mapper.UserMapper;
import az.baku.divfinalproject.repository.AdvertRepository;
import az.baku.divfinalproject.repository.AdvertTypeRepository;
import az.baku.divfinalproject.repository.RoleRepository;
import az.baku.divfinalproject.repository.UserRepository;
import az.baku.divfinalproject.service.AdvertService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdvertServiceImpl implements AdvertService {
    private final AdvertRepository advertRepository;
    private final UserRepository userRepository;
    private final AdvertMapper advertMapper;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final AdvertTypeRepository advertTypeRepository;
    private final UserRepository repository;

    @Override
    public AdvertResponse create(AdvertRequest request) {
        Advert advert = new Advert();
        AdvertType advertType = advertTypeRepository.findByType(request.getAdvertType());
        advert.setAdvertType(advertType);
        advert.setDescription(request.getDescription());
        advert.setAmountMonthly(request.getAmountMonthly());
        Optional<User> user = repository.findById(request.getUserId());
        if (user.isPresent()) {
            if (user.get().getPhoneNumber() != null) {
                advert.setUser(user.get());
            }else {
                throw new ApplicationException(new ExceptionResponse(ExceptionEnums.PAYMENT_SUCCEEDED.getMessage(), HttpStatus.NOT_FOUND));
            }
        }
        Advert saved = advertRepository.save(advert);
        return advertMapper.toResponse(saved);
    }

    @Override
    public AdvertResponse update(long id, AdvertRequest request) {
        Advert advert = advertRepository.findById(id).orElseThrow(() -> new RuntimeException("Error: Advert is not found."));
        return advertMapper.toResponse(advertRepository.save(advertMapper.partialUpdate(request,advert)));
    }

    @Override
    public void delete(long id) {
        Advert advert = advertRepository.findById(id).orElseThrow(() -> new RuntimeException("Error: Advert is not found."));
        advert.setActive(false);
        advertRepository.save(advert);
    }

    @Override
    public AdvertResponse getById(long id) {
        Advert advert = advertRepository.findById(id).orElseThrow(() -> new RuntimeException("Error: Advert is not found."));
        return advertMapper.toResponse(advert);
    }

    @Override
    public Collection<AdvertResponse> findAll() {
        List<Advert> all = advertRepository.findAll();
        return all.stream().map(advertMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public List<AdvertResponse> getAdverts(long userId) {
        List<Advert> byUserId = advertRepository.findAllByUserId(userId);
        return byUserId.stream().map(advertMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public List<Advert> findAllByAmountMonthlyIsLessThanEqual(double amount) {
        return advertRepository.findAllByAmountMonthlyIsLessThanEqual(amount);
    }

    @Override
    public List<Advert> findAllByAdvertTypeId(long advertTypeId) {
        return advertRepository.findAllByAdvertTypeId(advertTypeId);
    }

    public UserResponse getContactInformation(Request<AdvertRequest> request) {
        Optional<User> user = userRepository.findById(request.getId());
        if (user.isPresent()) {
            UserResponse response = userMapper.toResponse(user.get());
                Optional<Advert> byId = advertRepository.findById(request.getRequest().getId());
                if (byId.isPresent()) {
                    Advert advert = byId.get();
                    Optional<Role> roleAdmin = roleRepository.findByName("ROLE_ADMIN");
                    if (user.get().getViewedAdverts().contains(advert) || user.get().getRoles().contains(roleAdmin.get())) {
                        return response;
                    }
                }
                response.setPhoneNumber("+xxxxxxxxxxxx");
            return response;
        }
        throw new ApplicationException(new ExceptionResponse(ExceptionEnums.USER_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND));
    }


}
