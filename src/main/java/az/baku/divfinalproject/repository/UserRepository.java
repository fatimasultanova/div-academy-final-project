package az.baku.divfinalproject.repository;

import az.baku.divfinalproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where u.email = ?1 or u.phoneNumber = ?1")
    Optional<User> findByPhoneNumberOrEmail(String username);
    Boolean existsByPhoneNumber(String phoneNumber);
    Optional<User> findByEmail(String email);
    Optional<User> findByPhoneNumber(String username);
    Boolean existsByEmail(String email);
    Optional<User> findById(long id);
}