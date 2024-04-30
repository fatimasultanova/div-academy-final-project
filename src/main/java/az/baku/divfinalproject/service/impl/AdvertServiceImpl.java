package az.baku.divfinalproject.service.impl;

import az.baku.divfinalproject.dto.request.AdvertRequest;
import az.baku.divfinalproject.dto.response.AdvertResponse;
import az.baku.divfinalproject.repository.AdvertRepository;
import az.baku.divfinalproject.service.AdvertService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdvertServiceImpl implements AdvertService {
    private final AdvertRepository advertRepository;


    @Override
    public AdvertResponse create(AdvertRequest request) {
        return null;
    }

    @Override
    public AdvertResponse update(long id, AdvertRequest request) {
        return null;
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public AdvertResponse getById(long id) {
        return null;
    }

    @Override
    public Collection<AdvertResponse> findAll() {
        return List.of();
    }

    @Override
    public List<AdvertResponse> getAdverts(int userId) {
        return List.of();
    }
}
