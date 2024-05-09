package az.baku.divfinalproject.service.impl;

import az.baku.divfinalproject.dto.request.BuildingTypeRequest;
import az.baku.divfinalproject.dto.response.BuildingTypeResponse;
import az.baku.divfinalproject.entity.BuildingType;
import az.baku.divfinalproject.mapper.BuildingTypeMapper;
import az.baku.divfinalproject.repository.BuildingTypeRepository;
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
public class BuildingTypeServiceImpl implements CrudService<BuildingTypeRequest, BuildingTypeResponse> {
    private static final Logger log = LoggerFactory.getLogger(BuildingTypeServiceImpl.class);
    private final BuildingTypeRepository repository;
    private final BuildingTypeMapper mapper;

    @Override
    public BuildingTypeResponse create(BuildingTypeRequest request) {
        BuildingType buildingType = mapper.toEntity(request);
        repository.save(buildingType);
        return mapper.toResponse(buildingType);
    }

    @Override
    public BuildingTypeResponse update(long id, BuildingTypeRequest request) {
        BuildingType buildingType = repository.findById(id).orElseThrow(() -> new RuntimeException("Error: Building Type is not found."));
        return mapper.toResponse(repository.save(mapper.partialUpdate(request,buildingType)));
    }

    @Override
    public void delete(long id) {
        repository.deleteById(id);
    }

    @Override
    public BuildingTypeResponse getById(long id) {
        BuildingType buildingType = repository.findById(id).orElseThrow(() -> new RuntimeException("Error: Building Type is not found."));
        return mapper.toResponse(buildingType);
    }

    @Override
    public Collection<BuildingTypeResponse> findAll() {
        List<BuildingType> all = repository.findAll();
        return all.stream().map(mapper::toResponse).collect(Collectors.toList());
    }

    public BuildingType findByType(String type) {
        Optional<BuildingType> byType = repository.findByType(type);
        return byType.orElse(null);
    }

    public BuildingTypeResponse getBuildingTypeByType(String type) {
        BuildingType buildingType = findByType(type);
        if (buildingType != null) {
            return mapper.toResponse(buildingType);
        } else {
            log.error("Building Type is not found: {}", type);
            throw new RuntimeException("Error: Building Type is not found.");
        }
    }
}
