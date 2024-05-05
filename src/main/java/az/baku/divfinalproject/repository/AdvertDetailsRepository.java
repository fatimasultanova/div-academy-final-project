package az.baku.divfinalproject.repository;

import az.baku.divfinalproject.entity.AdvertDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdvertDetailsRepository extends JpaRepository<AdvertDetails, Long> {
    Optional<AdvertDetails> findById(long id);
    List<AdvertDetails> findAllBySearchingCountIsLessThanEqual(int searchingCount);
    List<AdvertDetails> findAllByLivingCountIsLessThanEqual(int livingCount);
    List<AdvertDetails> findAllByGenderContainsIgnoreCase(String gender);
}