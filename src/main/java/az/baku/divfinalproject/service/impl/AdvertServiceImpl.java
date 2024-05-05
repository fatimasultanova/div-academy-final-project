package az.baku.divfinalproject.service.impl;

import az.baku.divfinalproject.dto.request.AdvertRequest;
import az.baku.divfinalproject.dto.response.AdvertResponse;
import az.baku.divfinalproject.entity.Advert;
import az.baku.divfinalproject.entity.AdvertType;
import az.baku.divfinalproject.mapper.AdvertMapper;
import az.baku.divfinalproject.repository.AdvertRepository;
import az.baku.divfinalproject.repository.AdvertTypeRepository;
import az.baku.divfinalproject.service.AdvertService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdvertServiceImpl implements AdvertService {
    private final AdvertRepository advertRepository;
    private final AdvertMapper advertMapper;
    private final AdvertTypeRepository advertTypeRepository;


    @Override
    public AdvertResponse create(AdvertRequest request) {
        Advert advert = new Advert();
        AdvertType advertType = advertTypeRepository.findByType(request.getAdvertType());
        advert.setAdvertType(advertType);
        advert.setDescription(request.getDescription());
        advert.setAmountMonthly(request.getAmountMonthly());
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

}
