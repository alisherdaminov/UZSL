package UZSL.application.service.authentication.auth;

import UZSL.application.dto.app.AppResponse;
import UZSL.application.dto.auth.LoginDTO;
import UZSL.application.dto.auth.ResponseDTO;
import UZSL.application.dto.auth.UserCreated;
import UZSL.application.mapper.AuthMapper;
import UZSL.domain.model.entity.auth.UserEntity;
import UZSL.domain.service.authentication.auth.AuthService;
import UZSL.shared.enums.UzSlRoles;
import UZSL.infrastructure.adapter.repository.auth.RolesRepository;
import UZSL.infrastructure.adapter.repository.auth.UserRepository;
import UZSL.application.service.authentication.refresh.RefreshTokenServiceImpl;
import UZSL.application.service.authentication.roles.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private RolesService rolesService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private RolesRepository rolesRepository;
    @Autowired
    private RefreshTokenServiceImpl refreshTokenService;
    @Autowired
    private AuthMapper authMapper;

    /// USER REGISTRATION
    @Override
    public AppResponse<String> userRegistration(UserCreated userCreated) {
        Optional<UserEntity> optional = userRepository.findByUsername(userCreated.getUsername());
        if (optional.isPresent()) {
            return new AppResponse<>("User exists");
        }
        UserEntity user = authMapper.toUserEntity(userCreated);
        userRepository.save(user);
        rolesService.createRole(user.getUserId(), UzSlRoles.ROLE_USER);
        return new AppResponse<>("User successfully registered!");
    }

    /// USER LOG IN
    @Override
    public AppResponse<ResponseDTO> login(LoginDTO loginDTO) {
        Optional<UserEntity> optional = userRepository.findByUsername(loginDTO.getUsername());
        if (optional.isEmpty()) {
            return new AppResponse<>("User not found!");
        }
        UserEntity user = optional.get();
        if (!bCryptPasswordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            return new AppResponse<>("Wrong password!");
        }
        ResponseDTO responseDTO = authMapper.buildResponseDTO(user);
        return new AppResponse<>(responseDTO, "success", new Date());
    }

    /// USER LOG OUT
    @Override
    public AppResponse<String> logout(Integer userId, String refreshToken) {
        refreshTokenService.deleteRefreshToken(userId, refreshToken);
        return new AppResponse<>();
    }


}
