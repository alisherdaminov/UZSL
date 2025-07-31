package UZSL.application.mapper;

import UZSL.shared.util.JwtUtil;
import UZSL.application.dto.auth.ResponseDTO;
import UZSL.application.dto.auth.UserCreated;
import UZSL.domain.model.entity.auth.UserEntity;
import UZSL.shared.enums.UzSlRoles;
import UZSL.domain.repository.auth.RolesRepository;
import UZSL.application.service.authentication.refresh.RefreshTokenServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AuthMapper {

    private final RolesRepository rolesRepository;
    private final RefreshTokenServiceImpl refreshTokenService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public AuthMapper(RolesRepository rolesRepository,
                      RefreshTokenServiceImpl refreshTokenService,
                      BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.rolesRepository = rolesRepository;
        this.refreshTokenService = refreshTokenService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    /// ENTITY TO DTO
    public UserEntity toUserEntity(UserCreated userCreated) {
        return UserEntity.builder().
                fullName(userCreated.getFullName()).
                username(userCreated.getUsername()).
                password(bCryptPasswordEncoder.encode(userCreated.getPassword())).
                createdAt(LocalDateTime.now()).
                build();
    }

    /// DTO TO ENTITY
    public ResponseDTO buildResponseDTO(UserEntity user) {
        ResponseDTO dto = new ResponseDTO();
        dto.setUserId(user.getUserId());
        dto.setFullName(user.getFullName());
        dto.setUsername(user.getUsername());
        UzSlRoles uzSlRole = rolesRepository.findByUzSlRoles(user.getUserId());
        dto.setRoles(uzSlRole);
        dto.setJwtToken(JwtUtil.encode(user.getUsername(), user.getUserId(), uzSlRole));
        dto.setRefreshToken(refreshTokenService.createRefreshToken(user.getUserId()).getRefreshToken());
        return dto;
    }
}
