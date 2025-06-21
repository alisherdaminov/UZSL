package UZSL.service.auth;

import UZSL.entity.auth.RefreshTokenEntity;
import UZSL.entity.auth.UserEntity;
import UZSL.exception.AppBadException;
import UZSL.repository.auth.RefreshTokenRepository;
import UZSL.repository.auth.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static UZSL.config.util.JwtUtil.generateRefreshToken;

@Service
public class RefreshTokenService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    // creation of Refresh Token
    public RefreshTokenEntity createRefreshToken(Integer userId) {
        RefreshTokenEntity entity = new RefreshTokenEntity();
        entity.setUser(userRepository.findById(userId).get());
        entity.setRefreshToken(generateRefreshToken());
        long refreshExpirationMs = 1000L * 60 * 60 * 24 * 7;
        entity.setExpiryDate(Instant.now().plusMillis(refreshExpirationMs));
        return refreshTokenRepository.save(entity);
    }

    // finding of token
    public Optional<RefreshTokenEntity> findByToken(String refreshToken) {
        return refreshTokenRepository.findByRefreshToken(refreshToken);
    }

    public RefreshTokenEntity isValidToken(RefreshTokenEntity entity) {
        if (entity.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(entity);
            throw new AppBadException("Refresh token is expired!");
        }
        return entity;
    }

    // deleting of token
    public void deleterRefreshToken(Integer userId, String refreshToken) {
        Optional<UserEntity> optional = userRepository.findById(userId);
        if (optional.isEmpty()) {
            throw new AppBadException("User id: " + userId + "is not found!");
        }
        UserEntity user = optional.get();
        refreshTokenRepository.deleteByUserIdAndToken(user.getUserId(), refreshToken);
    }
}
