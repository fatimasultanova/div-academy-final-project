package az.baku.divfinalproject.service.impl;

import az.baku.divfinalproject.dto.request.AdvertDetailsRequest;
import az.baku.divfinalproject.dto.response.AdvertDetailsResponse;
import az.baku.divfinalproject.entity.AdvertDetails;
import az.baku.divfinalproject.mapper.AdvertDetailsMapper;
import az.baku.divfinalproject.repository.AdvertDetailsRepository;
import az.baku.divfinalproject.service.CrudService;
import lombok.RequiredArgsConstructor;
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

    @Override
    public AdvertDetailsResponse create(AdvertDetailsRequest request) {
        AdvertDetails advertDetails = AdvertDetails.builder().moveTime(request.getMoveTime())
                .searchingCount(request.getSearchingCount())
                .livingCount(request.getLivingCount())
                .gender(request.getGender())
                .build();
        repository.save(advertDetails);
        return mapper.toResponse(advertDetails);
    }

    @Override
    public AdvertDetailsResponse update(long id, AdvertDetailsRequest request) {
        AdvertDetails advertDetails = repository.findById(id).orElseThrow(() -> new RuntimeException("Error: Advert Details is not found."));
        return mapper.toResponse(repository.save(mapper.partialUpdate(request,advertDetails)));
    }

    @Override
    public void delete(long id) {
        Optional<AdvertDetails> advertDetails = repository.findById(id);
        if (advertDetails.isPresent()) {
            repository.deleteById(id);
        }
    }

    @Override
    public AdvertDetailsResponse getById(long id) {
        Optional<AdvertDetails> advertDetails = repository.findById(id);
        return advertDetails.map(mapper::toResponse).orElse(null);
    }

    @Override
    public Collection<AdvertDetailsResponse> findAll() {
        List<AdvertDetails> advertDetails = repository.findAll();
        return advertDetails.stream().map(mapper::toResponse).collect(Collectors.toList());
    }

    public List<AdvertDetails> findBySearchingCount(int searchingCount) {
        return repository.findAllBySearchingCountIsLessThanEqual(searchingCount);
    }

    public List<AdvertDetails> findByLivingCount(int livingCount) {
        return repository.findAllByLivingCountIsLessThanEqual(livingCount);
    }

    public List<AdvertDetails> findByGender(String gender) {
        return repository.findAllByGenderContainsIgnoreCase(gender);
    }
}
