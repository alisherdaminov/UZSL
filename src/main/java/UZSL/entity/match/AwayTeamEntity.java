package UZSL.entity.match;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "away_team")
public class AwayTeamEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String awayTeamId;
    @Column(name = "home_team_name")
    private String awayTeamName;
    @Column(name = "goals_own")
    private int awayGoal;
    @Column(name = "played_games")
    private int playedGames;

    //linked with TeamsEntity
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "away_teams_id")
    private TeamsEntity awayTeamsEntity;

    // Away team logo id set
    @Column(name = "away_logo_id")
    private String awayTeamLogoId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "away_logo_id", insertable = false, updatable = false)
    private MatchLogoEntity matchLogoEntity;
}
