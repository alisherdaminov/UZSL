package UZSL.service.auth;

import UZSL.config.util.JwtUtil;
import UZSL.dto.app_response.AppResponse;
import UZSL.dto.auth.LoginDTO;
import UZSL.dto.auth.ResponseDTO;
import UZSL.dto.auth.UserCreated;
import UZSL.entity.auth.UserEntity;
import UZSL.enums.UzSlRoles;
import UZSL.repository.auth.RolesRepository;
import UZSL.repository.auth.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private RolesService rolesService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private RolesRepository rolesRepository;

    // user registration
    public AppResponse<String> userRegistration(UserCreated userCreated) {
        Optional<UserEntity> optional = userRepository.findByUsername(userCreated.getUsername());
        if (optional.isPresent()) {
            return new AppResponse<>("User exists");
        }
        UserEntity user = new UserEntity();
        user.setFullName(userCreated.getFullName());
        user.setUsername(userCreated.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(userCreated.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        userRepository.save(user);
        rolesService.createRole(user.getUserId(), UzSlRoles.ROLE_USER);
        return new AppResponse<>("User successfully registered!");
    }

    // user login
    public AppResponse<ResponseDTO> login(LoginDTO loginDTO) {
        Optional<UserEntity> optional = userRepository.findByUsername(loginDTO.getUsername());
        if (optional.isEmpty()) {
            return new AppResponse<>("User not found!");
        }
        UserEntity user = optional.get();
        if (!bCryptPasswordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            return new AppResponse<>("Wrong password!");
        }
        ResponseDTO responseDTO = buildResponseDTO(user);
        return new AppResponse<>(responseDTO, "success", new Date());
    }

    // DTO for Login
    private ResponseDTO buildResponseDTO(UserEntity user) {
        ResponseDTO dto = new ResponseDTO();
        dto.setUserId(user.getUserId());
        dto.setFullName(user.getFullName());
        dto.setUsername(user.getUsername());
        UzSlRoles uzSlRole = rolesRepository.findByUzSlRoles(user.getUserId());
        dto.setRoles(uzSlRole);
        dto.setJwtToken(JwtUtil.encode(user.getUsername(), user.getUserId(), uzSlRole));
        return dto;
    }
}
