package UZSL.entity.match;

import UZSL.entity.auth.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "match_logo")
@Getter
@Setter
public class MatchLogoEntity {
    @Id
    private String matchLogoId;
    @Column(name = "origenName")
    private String origenName;
    @Column(name = "extension")
    private String extension;
    @Column(name = "path")
    private String path;
    @Column(name = "size")
    private Long size;
    @Column(name = "url")
    private String url;
    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teams_table_id")
    private TeamsEntity teamsEntity;

}
