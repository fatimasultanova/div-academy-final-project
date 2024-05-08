package az.baku.divfinalproject.repository;

import az.baku.divfinalproject.entity.BuildingType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BuildingTypeRepository extends JpaRepository<BuildingType, Long> {
    Optional<BuildingType> findByType(String type);
}