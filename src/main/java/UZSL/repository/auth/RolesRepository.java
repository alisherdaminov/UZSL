package UZSL.repository.auth;

import UZSL.entity.auth.RolesEntity;
import UZSL.enums.UzSlRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepository extends JpaRepository<RolesEntity, Integer> {

    @Query("SELECT r.uzSlRoles FROM RolesEntity r WHERE r.roleUserId = ?1")
    UzSlRoles findByUzSlRoles(Integer roleUserId);
}
