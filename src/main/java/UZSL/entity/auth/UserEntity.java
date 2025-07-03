package UZSL.entity.auth;

import UZSL.entity.home.HomeNewsEntity;
import UZSL.entity.match.MatchLogoEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    // uz_sl_roles
    @OneToOne(mappedBy = "userEntity", fetch = FetchType.LAZY)
    private RolesEntity uzSlRolesEntity;

    // uz_sl_post
    @OneToMany(mappedBy = "userEntity", fetch = FetchType.LAZY)
    private List<HomeNewsEntity> uzSLPostEntities;

    //refresh
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<RefreshTokenEntity> refreshToken;

    // matches club logo
    @OneToOne(mappedBy = "userEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private MatchLogoEntity logoEntityList;
}
