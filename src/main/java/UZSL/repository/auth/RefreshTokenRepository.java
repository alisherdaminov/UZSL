package UZSL.repository.auth;

import UZSL.entity.auth.RefreshTokenEntity;
import UZSL.entity.auth.RolesEntity;
import UZSL.enums.UzSlRoles;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Integer> {

    Optional<RefreshTokenEntity> findByToken(String refreshToken);

    void deleteByUserIdAndToken(Integer userId, String token);

}
