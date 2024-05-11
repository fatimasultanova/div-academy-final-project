package az.baku.divfinalproject.service.impl;

import az.baku.divfinalproject.dto.request.BuildingTypeRequest;
import az.baku.divfinalproject.dto.response.BuildingTypeResponse;
import az.baku.divfinalproject.dto.response.ExceptionResponse;
import az.baku.divfinalproject.entity.BuildingType;
import az.baku.divfinalproject.exception.ApplicationException;
import az.baku.divfinalproject.exception.ExceptionEnums;
import az.baku.divfinalproject.mapper.BuildingTypeMapper;
import az.baku.divfinalproject.repository.BuildingTypeRepository;
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
public class BuildingTypeServiceImpl implements CrudService<BuildingTypeRequest, BuildingTypeResponse> {
    private static final Logger logger = LoggerFactory.getLogger(BuildingTypeServiceImpl.class);
    private final BuildingTypeRepository repository;
    private final BuildingTypeMapper mapper;

    @Override
    public BuildingTypeResponse create(BuildingTypeRequest request) {
        logger.info("Create Building Type process starting");
        BuildingType buildingType = mapper.toEntity(request);
        repository.save(buildingType);
        logger.info("Create Building Type process finished");
        return mapper.toResponse(buildingType);
    }

    @Override
    public BuildingTypeResponse update(long id, BuildingTypeRequest request) {
        logger.info("Update Building Type process starting for ID: {}", id);
        BuildingType buildingType = repository.findById(id).orElseThrow(() -> new ApplicationException(new ExceptionResponse(ExceptionEnums.BUILDING_TYPE_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND)));
        BuildingType updatedBuildingType = repository.save(mapper.partialUpdate(request, buildingType));
        logger.info("Update Building Type process finished for ID: {}", id);
        return mapper.toResponse(updatedBuildingType);
    }

    @Override
    public void delete(long id) {
        logger.info("Delete Building Type process starting for ID: {}", id);
        repository.deleteById(id);
        logger.info("Delete Building Type process finished for ID: {}", id);
    }

    @Override
    public BuildingTypeResponse getById(long id) {
        logger.info("Get Building Type process starting for ID: {}", id);
        BuildingType buildingType = repository.findById(id).orElseThrow(() -> new ApplicationException(new ExceptionResponse(ExceptionEnums.BUILDING_TYPE_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND)));
        logger.info("Get Building Type process finished for ID: {}", id);
        return mapper.toResponse(buildingType);
    }

    @Override
    public Collection<BuildingTypeResponse> findAll() {
        logger.info("Find all Building Types process starting");
        List<BuildingType> all = repository.findAll();
        logger.info("Find all Building Types process finished");
        return all.stream().map(mapper::toResponse).collect(Collectors.toList());
    }

    public BuildingType findByType(String type) {
        Optional<BuildingType> byType = repository.findByType(type);
        return byType.orElse(null);
    }

    public BuildingTypeResponse getBuildingTypeByType(String type) {
        logger.info("Get Building Type by Type process starting for Type: {}", type);
        BuildingType buildingType = findByType(type);
        if (buildingType != null) {
            logger.info("Building Type found: {} ", type);
            return mapper.toResponse(buildingType);
        } else {
            logger.error("Building Type is not found: {}", type);
            throw new ApplicationException(new ExceptionResponse(ExceptionEnums.BUILDING_TYPE_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND));
        }
    }
}
