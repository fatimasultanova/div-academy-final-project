package az.baku.divfinalproject.service.impl;

import az.baku.divfinalproject.dto.request.AdvertTypeRequest;
import az.baku.divfinalproject.dto.response.AdvertTypeResponse;
import az.baku.divfinalproject.dto.response.ExceptionResponse;
import az.baku.divfinalproject.entity.AdvertType;
import az.baku.divfinalproject.exception.ApplicationException;
import az.baku.divfinalproject.exception.ExceptionEnums;
import az.baku.divfinalproject.mapper.AdvertTypeMapper;
import az.baku.divfinalproject.repository.AdvertTypeRepository;
import az.baku.divfinalproject.service.CrudService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdvertTypeServiceImpl implements CrudService<AdvertTypeRequest, AdvertTypeResponse> {
    private final AdvertTypeRepository repository;
    private final AdvertTypeMapper mapper;
    private static final Logger logger = LoggerFactory.getLogger(AdvertTypeServiceImpl.class);

    @Override
    public AdvertTypeResponse create(AdvertTypeRequest request) {
        logger.info("Create Advert Type process starting");
        AdvertType advertType = mapper.toEntity(request);
        repository.save(advertType);
        logger.info("Create Advert Type process finished");
        return mapper.toResponse(advertType);
    }

    @Override
    public AdvertTypeResponse update(long id, AdvertTypeRequest request) {
        logger.info("Update Advert Type process starting for ID: {}", id);
        AdvertType advertType = repository.findById(id).orElseThrow(() -> new ApplicationException(new ExceptionResponse(ExceptionEnums.ADVERT_TYPE_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND)));
        AdvertType updatedAdvertType = repository.save(mapper.partialUpdate(request, advertType));
        logger.info("Update Advert Type process finished for ID: {}", id);
        return mapper.toResponse(updatedAdvertType);
    }

    @Override
    public void delete(long id) {
        logger.info("Delete Advert Type process starting for ID: {}", id);
        repository.deleteById(id);
        logger.info("Delete Advert Type process finished for ID: {}", id);
    }

    @Override
    public AdvertTypeResponse getById(long id) {
        logger.info("Get Advert Type process starting for ID: {}", id);
        AdvertType advertType = repository.findById(id).orElseThrow(() -> new ApplicationException(new ExceptionResponse(ExceptionEnums.ADVERT_TYPE_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND)));
        logger.info("Get Advert Type process finished for ID: {}", id);
        return mapper.toResponse(advertType);
    }

    @Override
    public Collection<AdvertTypeResponse> findAll() {
        logger.info("Find all Advert Types process starting");
        List<AdvertType> all = repository.findAll();
        logger.info("Find all Advert Types process finished");
        return all.stream().map(mapper::toResponse).collect(Collectors.toList());
    }

    public AdvertType findByType(String type) {
        return repository.findByType(type);
    }

    public AdvertTypeResponse getAdvertTypeByType(String type) {
        logger.info("Get Advert Type by Type process starting for Type: {}", type);
        AdvertType advertType = findByType(type);
        if (advertType != null) {
            logger.info("Get Advert Type by Type process finished for Type: {}", type);
            return mapper.toResponse(advertType);
        } else {
            logger.error("Advert Type is not found for Type: {}", type);
            throw new ApplicationException(new ExceptionResponse(ExceptionEnums.ADVERT_TYPE_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND));
        }
    }
}
