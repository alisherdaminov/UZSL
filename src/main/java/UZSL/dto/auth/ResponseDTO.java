package UZSL.dto.auth;

import UZSL.enums.UzSlRoles;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDTO {

    private Integer userId;
    private String fullName;
    private String username;
    private String password;
    private UzSlRoles roles;
    private String jwtToken;
    private String refreshToken;
    private LocalDateTime createdAt;
}
