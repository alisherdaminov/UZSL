package UZSL.repository.auth;

import UZSL.entity.auth.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    @Query("SELECT u FROM UserEntity u WHERE u.username = :username")
    Optional<UserEntity> findByUsername(@Param("username") String username);

}
