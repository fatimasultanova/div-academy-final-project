package az.baku.divfinalproject.service.impl;

import az.baku.divfinalproject.dto.request.AdvertDetailsRequest;
import az.baku.divfinalproject.dto.response.AdvertDetailsResponse;
import az.baku.divfinalproject.dto.response.ExceptionResponse;
import az.baku.divfinalproject.entity.AdvertDetails;
import az.baku.divfinalproject.exception.ApplicationException;
import az.baku.divfinalproject.exception.ExceptionEnums;
import az.baku.divfinalproject.mapper.AdvertDetailsMapper;
import az.baku.divfinalproject.repository.AdvertDetailsRepository;
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
public class AdvertDetailsServiceImpl implements CrudService<AdvertDetailsRequest, AdvertDetailsResponse> {
    private final AdvertDetailsRepository repository;
    private final AdvertDetailsMapper mapper;
    private final Logger logger = LoggerFactory.getLogger(AdvertDetailsServiceImpl.class);

    @Override
    public AdvertDetailsResponse create(AdvertDetailsRequest request) {
        logger.info("Create Advert details process starting");
        AdvertDetails advertDetails = AdvertDetails.builder().moveTime(request.getMoveTime())
                .searchingCount(request.getSearchingCount())
                .livingCount(request.getLivingCount())
                .gender(request.getGender())
                .build();
        AdvertDetails save = repository.save(advertDetails);
        logger.info("Create Advert details process finished {}", save.getId());
        return mapper.toResponse(advertDetails);
    }

    @Override
    public AdvertDetailsResponse update(long id, AdvertDetailsRequest request) {
        logger.info("Update Advert details process starting {}" , id);
        AdvertDetails advertDetails = repository.findById(id).orElseThrow(() -> new ApplicationException(new ExceptionResponse(ExceptionEnums.ADVERT_DETAILS_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND)));
        logger.info("Update Advert details process finished {}" , id);
        return mapper.toResponse(repository.save(mapper.partialUpdate(request,advertDetails)));
    }

    @Override
    public void delete(long id) {
        logger.info("Delete Advert details process starting {}" , id);
        Optional<AdvertDetails> advertDetails = repository.findById(id);
        if (advertDetails.isPresent()) {
            repository.deleteById(id);
            logger.info("Advert details deleted successfully: {}" , id);
        }
        throw new ApplicationException(new ExceptionResponse(ExceptionEnums.ADVERT_DETAILS_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND));
    }

    @Override
    public AdvertDetailsResponse getById(long id) {
        logger.info("Get Advert details process starting {}" , id);
        AdvertDetails advertDetails = repository.findById(id).orElseThrow(() -> new ApplicationException(new ExceptionResponse(ExceptionEnums.ADVERT_DETAILS_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND)));
        logger.info("Get Advert details process finished {}", id);
        return mapper.toResponse(advertDetails);
    }

    @Override
    public Collection<AdvertDetailsResponse> findAll() {
        logger.info("Find all Advert details process starting");
        List<AdvertDetails> advertDetails = repository.findAll();
        if (advertDetails.isEmpty()) {
            throw new ApplicationException(new ExceptionResponse(ExceptionEnums.ADVERT_DETAILS_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND));
        }
        logger.info("Find all Advert details process finished");
        return advertDetails.stream().map(mapper::toResponse).collect(Collectors.toList());
    }

    public List<AdvertDetailsResponse> findBySearchingCount(int searchingCount) {
        logger.info("Find Advert details by searching count process starting {}" , searchingCount);
        List<AdvertDetails> allBySearchingCountIsLessThanEqual = repository.findAllBySearchingCountIsLessThanEqual(searchingCount);
        if (allBySearchingCountIsLessThanEqual.isEmpty()) {
            throw new ApplicationException(new ExceptionResponse(ExceptionEnums.ADVERT_DETAILS_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND));
        }
        logger.info("Find Advert details by searching count process finished {}", allBySearchingCountIsLessThanEqual.size());
        return allBySearchingCountIsLessThanEqual.stream().map(mapper::toResponse).collect(Collectors.toList());
    }

    public List<AdvertDetailsResponse> findByLivingCount(int livingCount) {
        logger.info("Find Advert details by living count process starting {}" , livingCount);
        List<AdvertDetails> allByLivingCountIsLessThanEqual = repository.findAllByLivingCountIsLessThanEqual(livingCount);
        if (allByLivingCountIsLessThanEqual.isEmpty()) {
            throw new ApplicationException(new ExceptionResponse(ExceptionEnums.ADVERT_DETAILS_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND));
        }
        logger.info("Find Advert details by living count process finished {}" , allByLivingCountIsLessThanEqual.size());
        return allByLivingCountIsLessThanEqual.stream().map(mapper::toResponse).collect(Collectors.toList());
    }

    public List<AdvertDetailsResponse> findByGender(String gender) {
        logger.info("Find Advert details by gender process starting {}" , gender);
        List<AdvertDetails> allByGenderContainsIgnoreCase = repository.findAllByGenderContainsIgnoreCase(gender);
        if (allByGenderContainsIgnoreCase.isEmpty()) {
            throw new ApplicationException(new ExceptionResponse(ExceptionEnums.ADVERT_DETAILS_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND));
        }
        logger.info("Find Advert details process by gender finished {}", allByGenderContainsIgnoreCase.size());
        return allByGenderContainsIgnoreCase.stream().map(mapper::toResponse).collect(Collectors.toList());
    }
}