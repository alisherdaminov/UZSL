package UZSL.domain.model.entity.match;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "away_team")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AwayTeamEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String awayTeamId;
    @Column(name = "away_team_name")
    private String awayTeamName;
    @Column(name = "away_goal")
    private int awayGoal;

    //linked with TeamsEntity
    @OneToOne(mappedBy = "awayTeamEntity", fetch = FetchType.LAZY)
    private TeamsEntity teamsEntity;

    // Away team logo id set
    @Column(name = "away_logo_id")
    private String awayTeamLogoId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "away_logo_id", insertable = false, updatable = false)
    private MatchLogoEntity matchLogoEntity;
}
