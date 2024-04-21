package az.baku.divfinalproject.repository;

import az.baku.divfinalproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}