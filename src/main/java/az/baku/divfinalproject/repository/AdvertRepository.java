package az.baku.divfinalproject.repository;

import az.baku.divfinalproject.entity.Advert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdvertRepository extends JpaRepository<Advert, Long> {
}