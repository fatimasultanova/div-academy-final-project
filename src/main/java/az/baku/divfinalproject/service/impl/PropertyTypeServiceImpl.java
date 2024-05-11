package az.baku.divfinalproject.service.impl;

import az.baku.divfinalproject.dto.request.PropertyTypeRequest;
import az.baku.divfinalproject.dto.response.ExceptionResponse;
import az.baku.divfinalproject.dto.response.PropertyTypeResponse;
import az.baku.divfinalproject.entity.PropertyType;
import az.baku.divfinalproject.exception.ApplicationException;
import az.baku.divfinalproject.exception.ExceptionEnums;
import az.baku.divfinalproject.mapper.PropertyTypeMapper;
import az.baku.divfinalproject.repository.PropertyTypeRepository;
import az.baku.divfinalproject.service.CrudService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PropertyTypeServiceImpl implements CrudService<PropertyTypeRequest, PropertyTypeResponse> {
    private static final Logger logger = LoggerFactory.getLogger(PropertyTypeServiceImpl.class);
    private final PropertyTypeRepository repository;
    private final PropertyTypeMapper mapper;

    @Override
    public PropertyTypeResponse create(PropertyTypeRequest request) {
        logger.info("Create Property Type process starting");
        PropertyType propertyType = mapper.toEntity(request);
        repository.save(propertyType);
        logger.info("Create Property Type process finished");
        return mapper.toResponse(propertyType);
    }

    @Override
    public PropertyTypeResponse update(long id, PropertyTypeRequest request) {
        logger.info("Update Property Type process starting for ID: {}", id);
        PropertyType propertyType = repository.findById(id).orElseThrow(() -> new ApplicationException(new ExceptionResponse(ExceptionEnums.PROPERTY_TYPE_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND)));
        PropertyType updatedPropertyType = repository.save(mapper.partialUpdate(request, propertyType));
        logger.info("Update Property Type process finished for ID: {}", id);
        return mapper.toResponse(updatedPropertyType);
    }

    @Override
    public void delete(long id) {
        logger.info("Delete Property Type process starting for ID: {}", id);
        repository.deleteById(id);
        logger.info("Delete Property Type process finished for ID: {}", id);
    }

    @Override
    public PropertyTypeResponse getById(long id) {
        logger.info("Get Property Type process starting for ID: {}", id);
        PropertyType propertyType = repository.findById(id).orElseThrow(() -> new ApplicationException(new ExceptionResponse(ExceptionEnums.PROPERTY_TYPE_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND)));
        logger.info("Get Property Type process finished for ID: {}", id);
        return mapper.toResponse(propertyType);
    }

    @Override
    public Collection<PropertyTypeResponse> findAll() {
        logger.info("Find all Property Types process starting");
        List<PropertyType> all = repository.findAll();
        logger.info("Find all Property Types process finished");
        return all.stream().map(mapper::toResponse).collect(Collectors.toList());
    }

    public PropertyType findByType(String type) {
        logger.info("Find Property Type by Type process starting for Type: {}", type);
        Optional<PropertyType> byType = repository.findByType(type);
        PropertyType propertyType = byType.orElse(null);
        logger.info("Find Property Type by Type process finished for Type: {}", type);
        return propertyType;
    }

    public PropertyTypeResponse getPropertyTypeByType(String type) {
        logger.info("Get Property Type by Type process starting for Type: {}", type);
        PropertyType propertyType = findByType(type);
        if (propertyType != null) {
            logger.info("Property Type found: {}", type);
            return mapper.toResponse(propertyType);
        } else {
            logger.error("Property Type is not found: {}", type);
            throw new ApplicationException(new ExceptionResponse(ExceptionEnums.PROPERTY_TYPE_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND));
        }
    }

}