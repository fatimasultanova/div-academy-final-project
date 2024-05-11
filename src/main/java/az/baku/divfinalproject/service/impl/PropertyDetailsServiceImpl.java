package az.baku.divfinalproject.service.impl;

import az.baku.divfinalproject.dto.request.PropertyDetailsRequest;
import az.baku.divfinalproject.dto.response.ExceptionResponse;
import az.baku.divfinalproject.dto.response.PropertyDetailsResponse;
import az.baku.divfinalproject.entity.PropertyDetails;
import az.baku.divfinalproject.exception.ApplicationException;
import az.baku.divfinalproject.exception.ExceptionEnums;
import az.baku.divfinalproject.mapper.PropertyDetailsMapper;
import az.baku.divfinalproject.repository.PropertyDetailsRepository;
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
public class PropertyDetailsServiceImpl implements CrudService<PropertyDetailsRequest, PropertyDetailsResponse> {
    private final Logger logger = LoggerFactory.getLogger(PropertyDetailsServiceImpl.class);
    private final PropertyDetailsRepository repository;
    private final PropertyDetailsMapper mapper;

    @Override
    public PropertyDetailsResponse create(PropertyDetailsRequest request) {
        logger.info("Create Property Details process starting");
        PropertyDetails propertyDetails = PropertyDetails.builder()
                .numberFloors(request.getNumberFloors())
                .roomFloor(request.getRoomFloor())
                .gas(request.isGas())
                .squareM(request.getSquare_m())
                .address(request.getAddress())
                .build();
        repository.save(propertyDetails);
        logger.info("Create Property Details process finished");
        return mapper.toResponse(propertyDetails);
    }

    @Override
    public PropertyDetailsResponse update(long id, PropertyDetailsRequest request) {
        logger.info("Update Property Details process starting for ID: {}", id);
        PropertyDetails propertyDetails = repository.findById(id).orElseThrow(() -> new ApplicationException(new ExceptionResponse(ExceptionEnums.PROPERTY_DETAILS_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND)));
        PropertyDetails updatedPropertyDetails = repository.save(mapper.partialUpdate(request, propertyDetails));
        logger.info("Update Property Details process finished for ID: {}", id);
        return mapper.toResponse(updatedPropertyDetails);
    }

    @Override
    public void delete(long id) {
        logger.info("Delete Property Details process starting for ID: {}", id);
        Optional<PropertyDetails> details = repository.findById(id);
        details.ifPresent(repository::delete);
        logger.info("Delete Property Details process finished for ID: {}", id);
    }

    @Override
    public PropertyDetailsResponse getById(long id) {
        logger.info("Get Property Details process starting for ID: {}", id);
        Optional<PropertyDetails> details = repository.findById(id);
        PropertyDetails propertyDetails = details.orElseThrow(() -> new ApplicationException(new ExceptionResponse(ExceptionEnums.PROPERTY_DETAILS_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND)));
        logger.info("Get Property Details process finished for ID: {}", id);
        return mapper.toResponse(propertyDetails);
    }

    @Override
    public Collection<PropertyDetailsResponse> findAll() {
        logger.info("Find all Property Details process starting");
        List<PropertyDetails> propertyDetails = repository.findAll();
        logger.info("Find all Property Details process finished");
        return propertyDetails.stream().map(mapper::toResponse).collect(Collectors.toList());
    }

    public List<PropertyDetails> findByNumberFloors(int numberFloors) {
        logger.info("Find Property Details by Number of Floors process starting for number of floors: {}", numberFloors);
        List<PropertyDetails> details = repository.findAllByNumberFloorsIsLessThanEqual(numberFloors);
        logger.info("Find Property Details by Number of Floors process finished for number of floors: {}, found {} records", numberFloors, details.size());
        return details;
    }

    public List<PropertyDetails> findByRoomFloor(int roomFloor) {
        logger.info("Find Property Details by Room Floor process starting for room floor: {}", roomFloor);
        List<PropertyDetails> details = repository.findAllByRoomFloorIsLessThanEqual(roomFloor);
        logger.info("Find Property Details by Room Floor process finished for room floor: {}, found {} records", roomFloor, details.size());
        return details;
    }

    public List<PropertyDetails> findByGas(boolean isGas) {
        logger.info("Find Property Details by Gas process starting for gas: {}", isGas);
        List<PropertyDetails> details;
        if (isGas) {
            details = repository.findAllByGasIsTrue();
        } else {
            details = repository.findAllByGasIsFalse();
        }
        logger.info("Find Property Details by Gas process finished for gas: {}, found {} records", isGas, details.size());
        return details;
    }

    public List<PropertyDetails> findBySquareM(long square_m) {
        logger.info("Find Property Details by Square Meters process starting for square meters: {}", square_m);
        List<PropertyDetails> details = repository.findAllBySquareMIsLessThanEqual(square_m);
        logger.info("Find Property Details by Square Meters process finished for square meters: {}, found {} records", square_m, details.size());
        return details;
    }

    public List<PropertyDetails> findByAddress(String address) {
        logger.info("Find Property Details by Address process starting for address: {}", address);
        List<PropertyDetails> details = repository.findAllByAddressContainsIgnoreCase(address);
        logger.info("Find Property Details by Address process finished for address: {}, found {} records", address, details.size());
        return details;
    }

    public List<PropertyDetails> findByElevator(boolean elevator) {
        logger.info("Find Property Details by Elevator process starting for elevator: {}", elevator);
        List<PropertyDetails> details;
        if (elevator) {
            details = repository.findAllByElevatorIsTrue();
        } else {
            details = repository.findAllByElevatorIsFalse();
        }
        logger.info("Find Property Details by Elevator process finished for elevator: {}, found {} records", elevator, details.size());
        return details;
    }
}
