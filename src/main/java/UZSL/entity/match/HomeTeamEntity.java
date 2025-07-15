package UZSL.entity.match;

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
    @Column(name = "played_games")
    private int playedGames;

    //linked with TeamsEntity
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "home_teams_id")
    private TeamsEntity homeTeamsEntity;

    // Home team logo id set
    @Column(name = "home_logo_id")
    private String homeTeamLogoId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "home_logo_id", insertable = false, updatable = false)
    private MatchLogoEntity matchLogoEntity;
}
