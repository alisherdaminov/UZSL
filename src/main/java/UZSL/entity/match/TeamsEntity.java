package UZSL.entity.match;

import UZSL.entity.clubs.clubsInfo.ClubsSquadEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "teams")
public class TeamsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String teamsId;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "home_team_id", referencedColumnName = "homeTeamId")
    private HomeTeamEntity homeTeamEntity;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "away_team_id", referencedColumnName = "awayTeamId")
    private AwayTeamEntity awayTeamEntity;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "clubs_squad")
    private ClubsSquadEntity clubsSquadEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teams_match")
    private MatchEntity teamsMatchEntity;


}
