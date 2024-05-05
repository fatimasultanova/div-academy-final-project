package az.baku.divfinalproject.repository;

import az.baku.divfinalproject.entity.AdvertType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdvertTypeRepository extends JpaRepository<AdvertType, Long> {
    AdvertType findByType(String name);
}