package az.baku.divfinalproject.repository;

import az.baku.divfinalproject.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
}