package UZSL.service.authentication.auth;

import UZSL.dto.app.AppResponse;
import UZSL.dto.auth.LoginDTO;
import UZSL.dto.auth.ResponseDTO;
import UZSL.dto.auth.UserCreated;
import UZSL.mapper.AuthMapper;
import UZSL.entity.auth.UserEntity;
import UZSL.enums.UzSlRoles;
import UZSL.repository.auth.RolesRepository;
import UZSL.repository.auth.UserRepository;
import UZSL.service.authentication.refresh.RefreshTokenServiceImpl;
import UZSL.service.authentication.roles.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
