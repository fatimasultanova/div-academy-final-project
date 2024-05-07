package az.baku.divfinalproject.repository;

import az.baku.divfinalproject.entity.UserVerify;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserVerifyRepository extends JpaRepository<UserVerify, Long> {
    Optional<UserVerify> findByToken(String token);
    List<UserVerify> findAllByUser_Id(long user);
}