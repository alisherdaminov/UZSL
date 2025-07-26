package UZSL.entity.match;

import UZSL.entity.clubs.clubsInfo.ClubsSquadEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "home_team")
public class HomeTeamEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String homeTeamId;
    @Column(name = "home_team_name")
    private String homeTeamName;
    @Column(name = "own_goal")
    private int ownGoal;

    //linked with TeamsEntity
    @OneToOne(mappedBy = "homeTeamEntity", fetch = FetchType.LAZY)
    private TeamsEntity teamsEntity;

    // Home team logo id set
    @Column(name = "home_logo_id")
    private String homeTeamLogoId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "home_logo_id", insertable = false, updatable = false)
    private MatchLogoEntity matchLogoEntity;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "clubs_squad_id")
    private ClubsSquadEntity clubsSquadEntity;
}
