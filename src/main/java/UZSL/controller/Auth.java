package UZSL.controller;

import UZSL.dto.app_response.AppResponse;
import UZSL.dto.auth.LoginDTO;
import UZSL.dto.auth.ResponseDTO;
import UZSL.dto.auth.UserCreated;
import UZSL.service.auth.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Auth", description = "User can use authorization and authentication for Uzbekistan super league's matches, news, videos and statistics")
public class Auth {

    @Autowired
    private AuthService authService;

    @PostMapping("/registration")
    public ResponseEntity<AppResponse<String>> userRegistration(@Valid @RequestBody UserCreated userCreated) {
        return ResponseEntity.ok().body(authService.userRegistration(userCreated));
    }

    @PostMapping("/login")
    public ResponseEntity<AppResponse<ResponseDTO>> login(@Valid @RequestBody LoginDTO loginDTO) {
        return ResponseEntity.ok().body(authService.login(loginDTO));
    }

}
