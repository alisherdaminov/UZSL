package UZSL.entity.match;

import UZSL.entity.auth.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Table(name = "matches")
@Entity
@Getter
@Setter
public class MatchEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String matchId;
    @Column(name = "match_started_date")
    private String matchStartedDate;
    @Column(name = "match_started_time")
    private String matchStartedTime;

    // uz_sl_admin
    @Column(name = "user_id")
    private Integer userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private UserEntity userEntity;

    // ClubsMatchInfoEntity is linked to MatchEntity in list
    @OneToMany(mappedBy = "matchEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClubsMatchInfoEntity> clubsMatchInfoList;

    // matchEntity linked into MatchLogoEntity
    @Column(name = "match_home_logo_id")
    private String homeTeamLogoId;

    @Column(name = "match_visitor_logo_id")
    private String visitorLogoId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "matches_logo_id", insertable = false, updatable = false)
    private MatchLogoEntity matchLogoEntity;

}
