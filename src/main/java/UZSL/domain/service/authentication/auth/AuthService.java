package UZSL.domain.service.authentication.auth;

import UZSL.application.dto.app.AppResponse;
import UZSL.application.dto.auth.LoginDTO;
import UZSL.application.dto.auth.ResponseDTO;
import UZSL.application.dto.auth.UserCreated;

public interface AuthService {

    AppResponse<String> userRegistration(UserCreated userCreated);

    AppResponse<ResponseDTO> login(LoginDTO loginDTO);

    AppResponse<String> logout(Integer userId, String refreshToken);
}
