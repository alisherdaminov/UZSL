package UZSL.infrastructure.config.validation;

import UZSL.domain.model.entity.auth.UserEntity;
import UZSL.shared.enums.UzSlRoles;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
public class CustomUserDetails implements UserDetails {

    private Integer userId;
    private String fullName;
    private String username;
    private String password;
    private final GrantedAuthority authority;

    public CustomUserDetails(UserEntity userEntity, UzSlRoles uzSlRoles) {
        this.userId = userEntity.getUserId();
        this.fullName = userEntity.getFullName();
        this.username = userEntity.getUsername();
        this.password = userEntity.getPassword();
        this.authority = new SimpleGrantedAuthority(uzSlRoles.name());
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(authority);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
