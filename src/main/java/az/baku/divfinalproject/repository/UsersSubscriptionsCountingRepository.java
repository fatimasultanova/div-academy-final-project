package az.baku.divfinalproject.repository;

import az.baku.divfinalproject.entity.User;
import az.baku.divfinalproject.entity.UsersSubscriptionsCounting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersSubscriptionsCountingRepository extends JpaRepository<UsersSubscriptionsCounting, Long> {
    Optional<UsersSubscriptionsCounting> findByUser(User user);
}