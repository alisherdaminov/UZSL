package UZSL.application.dto.auth;

import UZSL.shared.enums.UzSlRoles;
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
