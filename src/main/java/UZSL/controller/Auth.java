package UZSL.controller;

import UZSL.config.util.JwtUtil;
import UZSL.dto.app.AppResponse;
import UZSL.dto.auth.LoginDTO;
import UZSL.dto.auth.RefreshTokenRequest;
import UZSL.dto.auth.ResponseDTO;
import UZSL.dto.auth.UserCreated;
import UZSL.entity.auth.RefreshTokenEntity;
import UZSL.service.auth.AuthService;
import UZSL.service.auth.RefreshTokenService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Auth", description = "User can use authorization and authentication for Uzbekistan super league's matches, news, videos and statistics")
public class Auth {

    @Autowired
    private AuthService authService;
    @Autowired
    private RefreshTokenService refreshTokenService;

    @PostMapping("/registration")
    public ResponseEntity<AppResponse<String>> userRegistration(@Valid @RequestBody UserCreated userCreated) {
        return ResponseEntity.ok().body(authService.userRegistration(userCreated));
    }

    @PostMapping("/login")
    public ResponseEntity<AppResponse<ResponseDTO>> login(@Valid @RequestBody LoginDTO loginDTO) {
        return ResponseEntity.ok().body(authService.login(loginDTO));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AppResponse> refreshToken(@RequestBody RefreshTokenRequest request) {
        String refreshToken = request.getRefreshToken();

        RefreshTokenEntity entity = refreshTokenService.findByToken(refreshToken)
                .map(refreshTokenService::isValidToken)
                .orElseThrow(() -> new RuntimeException("Refresh token is not found!"));

        String generatedToken = JwtUtil.encode(entity.getUser().getUsername(), entity.getUser().getUserId(),
                entity.getUser().getUzSlRolesEntity().getUzSlRoles());

        return ResponseEntity.ok().body(new AppResponse<>(generatedToken, "success", new Date()));
    }

    @PostMapping("/logout/{userId}")
    public AppResponse<String> logout(@PathVariable("userId") Integer userId, String refreshToken) {
        authService.logout(userId, refreshToken);
        return new AppResponse<>("Logged out!", "success", new Date());
    }

}
