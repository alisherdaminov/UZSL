package UZSL.dto.auth;

import UZSL.enums.UzSlRoles;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JwtDTO {
    private Integer userId;
    private String username;
    private UzSlRoles uzSlRoles;
}
