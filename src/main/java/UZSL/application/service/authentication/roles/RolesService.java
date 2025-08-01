package UZSL.application.service.authentication.roles;

import UZSL.domain.model.entity.auth.RolesEntity;
import UZSL.shared.enums.UzSlRoles;
import UZSL.domain.repository.auth.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * RolesService is for creation of users role and saving into the DATABASE which is RolesRepository
 * */
@Service
public class RolesService {

    @Autowired
    private RolesRepository rolesRepository;

    // creating of user's role
    public void createRole(Integer userId, UzSlRoles uzSlRoles) {
        RolesEntity roles = new RolesEntity();
        roles.setRoleUserId(userId);
        roles.setUzSlRoles(uzSlRoles);
        roles.setCreatedAt(LocalDateTime.now());
        rolesRepository.save(roles);
    }

}
