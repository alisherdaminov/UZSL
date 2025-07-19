package UZSL.service.auth;

import UZSL.dto.app.AppResponse;
import UZSL.dto.auth.LoginDTO;
import UZSL.dto.auth.ResponseDTO;
import UZSL.dto.auth.UserCreated;

public interface AuthService {

    AppResponse<String> userRegistration(UserCreated userCreated);

    AppResponse<ResponseDTO> login(LoginDTO loginDTO);

    AppResponse<String> logout(Integer userId, String refreshToken);
}
