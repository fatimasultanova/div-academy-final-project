package az.baku.divfinalproject.service.impl;

import az.baku.divfinalproject.dto.request.PropertyDetailsRequest;
import az.baku.divfinalproject.dto.response.PropertyDetailsResponse;
import az.baku.divfinalproject.entity.PropertyDetails;
import az.baku.divfinalproject.mapper.PropertyDetailsMapper;
import az.baku.divfinalproject.repository.PropertyDetailsRepository;
import az.baku.divfinalproject.service.CrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PropertyDetailsServiceImpl implements CrudService<PropertyDetailsRequest, PropertyDetailsResponse> {
    private final PropertyDetailsRepository repository;
    private final PropertyDetailsMapper mapper;

    @Override
    public PropertyDetailsResponse create(PropertyDetailsRequest request) {
        PropertyDetails propertyDetails = PropertyDetails.builder()
                .numberFloors(request.getNumberFloors())
                .roomFloor(request.getRoomFloor())
                .gas(request.isGas())
                .squareM(request.getSquare_m())
                .address(request.getAddress())
                .build();
        repository.save(propertyDetails);
        return mapper.toResponse(propertyDetails);
    }

    @Override
    public PropertyDetailsResponse update(long id, PropertyDetailsRequest request) {
        PropertyDetails propertyDetails = repository.findById(id).orElse(null);
        return mapper.toResponse(repository.save(mapper.partialUpdate(request,propertyDetails)));
    }

    @Override
    public void delete(long id) {
        Optional<PropertyDetails> details = repository.findById(id);
        details.ifPresent(repository::delete);
    }

    @Override
    public PropertyDetailsResponse getById(long id) {
        Optional<PropertyDetails> details = repository.findById(id);
        return details.map(mapper::toResponse).orElse(null);
    }

    @Override
    public Collection<PropertyDetailsResponse> findAll() {
        List<PropertyDetails> propertyDetails = repository.findAll();
        return propertyDetails.stream().map(mapper::toResponse).collect(Collectors.toList());

    }

    public List<PropertyDetails> findByNumberFloors(int numberFloors) {
        return repository.findAllByNumberFloorsIsLessThanEqual(numberFloors);
    }

    public List<PropertyDetails> findByRoomFloor(int roomFloor) {
        return repository.findAllByRoomFloorIsLessThanEqual(roomFloor);
    }

    public List<PropertyDetails> findByGas(boolean isGas) {
        if (isGas) {
            return repository.findAllByGasIsTrue();
        }else {
            return repository.findAllByGasIsFalse();
        }
    }
    public List<PropertyDetails> findBySquareM(long isSquare_m) {
        return repository.findAllBySquareMIsLessThanEqual(isSquare_m);
    }
    public List<PropertyDetails> findByAddress(String address) {
        return repository.findAllByAddressContainsIgnoreCase(address);
    }

    public List<PropertyDetails> findByElevator(boolean elevator) {
        if (elevator) {
            return repository.findAllByElevatorIsTrue();
        }else {
            return repository.findAllByElevatorIsFalse();
        }
    }
}
