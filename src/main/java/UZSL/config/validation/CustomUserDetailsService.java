package UZSL.config.validation;

import UZSL.entity.auth.UserEntity;
import UZSL.enums.UzSlRoles;
import UZSL.repository.auth.UserRepository;
import UZSL.repository.auth.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RolesRepository rolesRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> optional = userRepository.findByUsername(username);
        if (optional.isEmpty()) {
            throw new UsernameNotFoundException("User not found!");
        }
        UserEntity userEntity = optional.get();
        UzSlRoles uzSlRoles = rolesRepository.findByUzSlRoles(userEntity.getUserId());
        return new CustomUserDetails(userEntity, uzSlRoles);
    }
}
