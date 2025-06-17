package UZSL.service;

import UZSL.entity.RolesEntity;
import UZSL.enums.UzSlRoles;
import UZSL.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RolesService {

    @Autowired
    private RolesRepository rolesRepository;

    public void createRole(Integer userId, UzSlRoles uzSlRoles) {
        RolesEntity roles = new RolesEntity();
        roles.setRoleUserId(userId);
        roles.setUzSlRoles(uzSlRoles);
        roles.setCreatedAt(LocalDateTime.now());
        rolesRepository.save(roles);
    }

}
