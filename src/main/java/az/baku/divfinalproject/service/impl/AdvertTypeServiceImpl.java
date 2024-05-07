package az.baku.divfinalproject.service.impl;

import az.baku.divfinalproject.dto.request.AdvertTypeRequest;
import az.baku.divfinalproject.dto.response.AdvertTypeResponse;
import az.baku.divfinalproject.entity.AdvertType;
import az.baku.divfinalproject.mapper.AdvertTypeMapper;
import az.baku.divfinalproject.repository.AdvertTypeRepository;
import az.baku.divfinalproject.service.CrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdvertTypeServiceImpl implements CrudService<AdvertTypeRequest, AdvertTypeResponse> {
    private final AdvertTypeRepository repository;
    private final AdvertTypeMapper mapper;

    @Override
    public AdvertTypeResponse create(AdvertTypeRequest request) {
        AdvertType advertType = mapper.toEntity(request);
        repository.save(advertType);
        return mapper.toResponse(advertType);
    }

    @Override
    public AdvertTypeResponse update(long id, AdvertTypeRequest request) {
        AdvertType advertType = repository.findById(id).orElseThrow(() -> new RuntimeException("Error: Advert is not found."));
        return mapper.toResponse(repository.save(mapper.partialUpdate(request,advertType)));
    }

    @Override
    public void delete(long id) {
        repository.deleteById(id);
    }

    @Override
    public AdvertTypeResponse getById(long id) {
        AdvertType advertType = repository.findById(id).orElseThrow(() -> new RuntimeException("Error: Advert is not found."));
        return mapper.toResponse(advertType);
    }

    @Override
    public Collection<AdvertTypeResponse> findAll() {
        List<AdvertType> all = repository.findAll();
        return all.stream().map(mapper::toResponse).collect(Collectors.toList());
    }

    public AdvertType findByType(String type) {
        return repository.findByType(type);
    }
}
