package UZSL.domain.model.entity.match;

import UZSL.domain.model.entity.auth.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "match_logo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
