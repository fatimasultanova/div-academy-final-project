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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdvertServiceImpl implements AdvertService {
    private final Logger logger = LoggerFactory.getLogger(AdvertServiceImpl.class);
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
        logger.info("Advert create process starting");
        AdvertType advertType = advertTypeRepository.findByType(request.getAdvertType());
        advert.setAdvertType(advertType);
        advert.setDescription(request.getDescription());
        advert.setAmountMonthly(request.getAmountMonthly());
        Optional<User> user = repository.findById(request.getUserId());
        if (user.isPresent()) {
            if (user.get().getPhoneNumber() != null) {
                advert.setUser(user.get());
            } else {
                throw new ApplicationException(new ExceptionResponse(ExceptionEnums.PHONE_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND));
            }

            Advert saved = advertRepository.save(advert);
            logger.info("Advert created: {}", saved.getId());
            return advertMapper.toResponse(saved);
        }
        throw new ApplicationException(new ExceptionResponse(ExceptionEnums.USER_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND));
    }

    @Override
    public AdvertResponse update(long id, AdvertRequest request) {
        logger.info("Update Advert process starting for ID: {}", id);
        Advert advert = advertRepository.findById(id).orElseThrow(() -> new ApplicationException(new ExceptionResponse(ExceptionEnums.ADVERT_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND)));
        advert.setUpdateDate(LocalDateTime.now());
        logger.info("Update Advert process finished for ID: {}", id);
        return advertMapper.toResponse(advertRepository.save(advertMapper.partialUpdate(request,advert)));
    }

    @Override
    public void delete(long id) {
        logger.info("Delete Advert process starting for ID: {}", id);
        Advert advert = advertRepository.findById(id).orElseThrow(() -> new ApplicationException(new ExceptionResponse(ExceptionEnums.ADVERT_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND)));
        advert.setActive(false);
        advertRepository.save(advert);
        logger.info("Delete Advert process finished for ID: {}", id);
    }

    @Override
    public AdvertResponse getById(long id) {
        logger.info("Get Advert process starting for ID: {}", id);
        Advert advert = advertRepository.findById(id).orElseThrow(() -> new ApplicationException(new ExceptionResponse(ExceptionEnums.ADVERT_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND)));
        logger.info("Get Advert process finished for ID: {}", id);
        return advertMapper.toResponse(advert);
    }

    @Override
    public Collection<AdvertResponse> findAll() {
        logger.info("Find all Adverts process starting");
        List<Advert> all = advertRepository.findAll();
        logger.info("Find all Adverts process finished");
        return all.stream().map(advertMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public List<AdvertResponse> getAdverts(long userId) {
        logger.info("Get Adverts process starting by user ID: {}", userId);
        List<Advert> byUserId = advertRepository.findAllByUserId(userId);
        logger.info("Get Adverts process finished by user ID: {}", userId);
        return byUserId.stream().map(advertMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public List<Advert> findAllByAmountMonthlyIsLessThanEqual(double amount) {
        logger.info("Find all Adverts process starting by monthly amount: {}", amount);
        return advertRepository.findAllByAmountMonthlyIsLessThanEqual(amount);
    }

    @Override
    public List<Advert> findAllByAdvertTypeId(long advertTypeId) {
        logger.info("Find all Adverts process starting by advert type: {}", advertTypeId);
        return advertRepository.findAllByAdvertTypeId(advertTypeId);
    }

    public UserResponse getContactInformation(Request<AdvertRequest> request) {
        logger.info("Get Contact Information process starting for Request: {}", request.getId());
        Optional<User> user = userRepository.findById(request.getId());
        if (user.isPresent()) {
            UserResponse response = userMapper.toResponse(user.get());
                Optional<Advert> byId = advertRepository.findById(request.getRequest().getId());
                if (byId.isPresent()) {
                    Advert advert = byId.get();
                    Optional<Role> roleAdmin = roleRepository.findByName("ROLE_ADMIN");
                    if (user.get().getViewedAdverts().contains(advert) || user.get().getRoles().contains(roleAdmin.get())) {
                        logger.info("Get Contact Information process finished successfully for Request: {} to {}", request.getId(),request.getRequest().getId());
                        return response;
                    }
                }
                response.setPhoneNumber("+xxxxxxxxxxxx");
            logger.info("Get Contact Information process finished for Request: {} to {}", request.getId(), request.getRequest().getId());
            return response;
        }
        logger.error("User not found for ID: {}", request.getId());
        throw new ApplicationException(new ExceptionResponse(ExceptionEnums.USER_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND));
    }
}