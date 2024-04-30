package az.baku.divfinalproject.repository;

import az.baku.divfinalproject.entity.Advert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface AdvertRepository extends JpaRepository<Advert, Long> {
    Optional<Advert> findById(long id);
    Boolean existsById(long id);
    List<Advert> findByAmountMonthly(double amount);
    List<Advert> findByUserId(long userId);
    List<Advert> findByAdvertTypeId(long advertTypeId);
}