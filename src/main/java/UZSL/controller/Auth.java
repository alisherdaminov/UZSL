package UZSL.controller;

import UZSL.dto.AppResponse;
import UZSL.dto.LoginDTO;
import UZSL.dto.ResponseDTO;
import UZSL.dto.UserCreated;
import UZSL.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/api/v1/auth")
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
