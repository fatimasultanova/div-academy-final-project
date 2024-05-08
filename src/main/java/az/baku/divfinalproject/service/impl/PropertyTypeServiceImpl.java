package az.baku.divfinalproject.service.impl;

import az.baku.divfinalproject.dto.request.PropertyTypeRequest;
import az.baku.divfinalproject.dto.response.PropertyTypeResponse;
import az.baku.divfinalproject.entity.PropertyType;
import az.baku.divfinalproject.mapper.PropertyTypeMapper;
import az.baku.divfinalproject.repository.PropertyTypeRepository;
import az.baku.divfinalproject.service.CrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PropertyTypeServiceImpl implements CrudService<PropertyTypeRequest, PropertyTypeResponse> {
    private final PropertyTypeRepository repository;
    private final PropertyTypeMapper mapper;

    @Override
    public PropertyTypeResponse create(PropertyTypeRequest request) {
        PropertyType PropertyType = mapper.toEntity(request);
        repository.save(PropertyType);
        return mapper.toResponse(PropertyType);
    }

    @Override
    public PropertyTypeResponse update(long id, PropertyTypeRequest request) {
        PropertyType PropertyType = repository.findById(id).orElseThrow(() -> new RuntimeException("Error: Property Type is not found."));
        return mapper.toResponse(repository.save(mapper.partialUpdate(request,PropertyType)));
    }

    @Override
    public void delete(long id) {
        repository.deleteById(id);
    }

    @Override
    public PropertyTypeResponse getById(long id) {
        PropertyType PropertyType = repository.findById(id).orElseThrow(() -> new RuntimeException("Error: Property Type is not found."));
        return mapper.toResponse(PropertyType);
    }

    @Override
    public Collection<PropertyTypeResponse> findAll() {
        List<PropertyType> all = repository.findAll();
        return all.stream().map(mapper::toResponse).collect(Collectors.toList());
    }

    public PropertyType findByType(String type) {
        Optional<PropertyType> byType = repository.findByType(type);
        if (byType.isPresent()) {
            return byType.get();
        }
        return null;
    }
}
