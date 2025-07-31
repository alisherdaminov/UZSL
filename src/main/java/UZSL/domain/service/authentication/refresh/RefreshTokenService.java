package UZSL.domain.service.authentication.refresh;

import UZSL.domain.model.entity.auth.RefreshTokenEntity;

import java.util.Optional;

public interface RefreshTokenService {

    RefreshTokenEntity createRefreshToken(Integer userId);

    Optional<RefreshTokenEntity> findByToken(String refreshToken);

    RefreshTokenEntity isValidToken(RefreshTokenEntity entity);

    void deleteRefreshToken(Integer userId, String refreshToken);
}
