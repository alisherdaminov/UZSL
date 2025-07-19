package UZSL.dto.extensions;

import UZSL.config.util.JwtUtil;
import UZSL.dto.auth.ResponseDTO;
import UZSL.entity.auth.UserEntity;
import UZSL.enums.UzSlRoles;
import UZSL.repository.auth.RolesRepository;
import UZSL.service.authentication.refresh.RefreshTokenServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthServiceDTO {
    private final RolesRepository rolesRepository;
    private final RefreshTokenServiceImpl refreshTokenService;

    @Autowired
    public AuthServiceDTO(RolesRepository rolesRepository, RefreshTokenServiceImpl refreshTokenService) {
        this.rolesRepository = rolesRepository;
        this.refreshTokenService = refreshTokenService;
    }

    // DTO for Login
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
