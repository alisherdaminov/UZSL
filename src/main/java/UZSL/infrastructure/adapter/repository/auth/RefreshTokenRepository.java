package UZSL.infrastructure.adapter.repository.auth;

import UZSL.domain.model.entity.auth.RefreshTokenEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Integer> {

    Optional<RefreshTokenEntity> findByRefreshToken(String refreshToken);

    @Transactional
    @Modifying
    @Query("DELETE FROM RefreshTokenEntity r WHERE r.user.id = :userId AND r.refreshToken = :token")
    void deleteByUserIdAndToken(@Param("userId") Integer userId, @Param("token") String token);


}
