package az.baku.divfinalproject.service.impl;

import az.baku.divfinalproject.dto.request.PropertyTypeRequest;
import az.baku.divfinalproject.dto.response.PropertyTypeResponse;
import az.baku.divfinalproject.entity.PropertyType;
import az.baku.divfinalproject.mapper.PropertyTypeMapper;
import az.baku.divfinalproject.repository.PropertyTypeRepository;
import az.baku.divfinalproject.service.CrudService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PropertyTypeServiceImpl implements CrudService<PropertyTypeRequest, PropertyTypeResponse> {
    private static final Logger log = LoggerFactory.getLogger(PropertyTypeServiceImpl.class);
    private final PropertyTypeRepository repository;
    private final PropertyTypeMapper mapper;

    @Override
    public PropertyTypeResponse create(PropertyTypeRequest request) {
        PropertyType propertyType = mapper.toEntity(request);
        repository.save(propertyType);
        return mapper.toResponse(propertyType);
    }

    @Override
    public PropertyTypeResponse update(long id, PropertyTypeRequest request) {
        PropertyType propertyType = repository.findById(id).orElseThrow(() -> new RuntimeException("Error: Property Type is not found."));
        return mapper.toResponse(repository.save(mapper.partialUpdate(request,propertyType)));
    }

    @Override
    public void delete(long id) {
        repository.deleteById(id);
    }

    @Override
    public PropertyTypeResponse getById(long id) {
        PropertyType propertyType = repository.findById(id).orElseThrow(() -> new RuntimeException("Error: Property Type is not found."));
        return mapper.toResponse(propertyType);
    }

    @Override
    public Collection<PropertyTypeResponse> findAll() {
        List<PropertyType> all = repository.findAll();
        return all.stream().map(mapper::toResponse).collect(Collectors.toList());
    }

    public PropertyType findByType(String type) {
        Optional<PropertyType> byType = repository.findByType(type);
        return byType.orElse(null);
    }

    public PropertyTypeResponse getPropertyTypeByType(String type) {
        PropertyType propertyType = findByType(type);
        if (propertyType != null) {
            return mapper.toResponse(propertyType);
        } else {
            log.error("PropertyType is not found: {}", type);
            throw new RuntimeException("PropertyType is not found");
        }
    }
}