package az.baku.divfinalproject.repository;

import az.baku.divfinalproject.entity.PropertyDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyDetailsRepository extends JpaRepository<PropertyDetails, Long> {
    List<PropertyDetails> findAllByNumberFloorsIsLessThanEqual(int numberFloors);
    List<PropertyDetails> findAllByRoomFloorIsLessThanEqual(int numberFloors);
    List<PropertyDetails> findAllByGasIsTrue();
    List<PropertyDetails> findAllByGasIsFalse();
    List<PropertyDetails> findAllByElevatorIsTrue();
    List<PropertyDetails> findAllByElevatorIsFalse();
    List<PropertyDetails> findAllByAddressContainsIgnoreCase(String address);
    List<PropertyDetails> findAllBySquareMIsLessThanEqual(long square);
}