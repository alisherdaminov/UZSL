package UZSL.application.dto.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreated {
    private String fullName;
    private String username;
    private String password;
}
