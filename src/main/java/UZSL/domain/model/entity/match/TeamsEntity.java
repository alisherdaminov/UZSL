package UZSL.domain.model.entity.match;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "teams")
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teams_match")
    private MatchEntity teamsMatchEntity;


}
