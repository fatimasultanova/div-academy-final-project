package az.baku.divfinalproject.repository;

import az.baku.divfinalproject.entity.UsersSubscriptionsCounting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersSubscriptionsCountingRepository extends JpaRepository<UsersSubscriptionsCounting, Long> {
}