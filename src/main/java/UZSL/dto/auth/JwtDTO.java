package UZSL.dto.auth;

import UZSL.enums.UzSlRoles;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtDTO {
    private Integer userId;
    private String username;
    private UzSlRoles uzSlRoles;
}
