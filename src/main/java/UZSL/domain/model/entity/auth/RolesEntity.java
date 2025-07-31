package UZSL.domain.model.entity.auth;

import UZSL.shared.enums.UzSlRoles;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "uz_sl_roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RolesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roleId;

    // roles
    @Column(name = "uz_sl_roles")
    @Enumerated(EnumType.STRING)
    private UzSlRoles uzSlRoles;

    // user role
    @Column(name = "role_user_id")
    private Integer roleUserId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_user_id", insertable = false, updatable = false)
    private UserEntity userEntity;

    // created at
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}
