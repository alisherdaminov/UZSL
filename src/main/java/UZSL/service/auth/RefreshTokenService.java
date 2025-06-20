package UZSL.service.auth;

import UZSL.entity.auth.RefreshTokenEntity;
import UZSL.exception.AppBadException;
import UZSL.repository.auth.RefreshTokenRepository;
import UZSL.repository.auth.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Value("${jwt.refreshExpirationMs}")
    private Long refreshTokenDuration;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenEntity createRefreshToken(Integer userId) {
        RefreshTokenEntity entity = new RefreshTokenEntity();
        entity.setUser(userRepository.findById(userId).get());
        entity.setRefreshToken(UUID.randomUUID().toString());
        entity.setExpiryDate(Instant.now().plusMillis(refreshTokenDuration));
        return refreshTokenRepository.save(entity);
    }

    public Optional<RefreshTokenEntity> findByToken(String refreshToken) {
        return refreshTokenRepository.findByToken(refreshToken);
    }

    public RefreshTokenEntity isValidToken(RefreshTokenEntity entity) {
        if (entity.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(entity);
            throw new AppBadException("Refresh token is expired!");
        }
        return entity;
    }
}
