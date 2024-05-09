package az.baku.divfinalproject.repository;

import az.baku.divfinalproject.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
   Subscription findByType(String type);
   Subscription findByAmount(long amount);
   List<Subscription> findAllByAmountIsLessThan(long amount);
}