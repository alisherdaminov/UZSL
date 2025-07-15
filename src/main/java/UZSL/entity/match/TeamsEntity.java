package UZSL.entity.match;

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

    //linked with HomeTeamEntity
    @OneToOne(mappedBy = "homeTeamsEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private HomeTeamEntity homeTeamEntity;

    //linked with AwayTeamEntity
    @OneToOne(mappedBy = "awayTeamsEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private AwayTeamEntity awayTeamEntity;

    // linked with parent MatchEntity
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teams_match")
    private MatchEntity teamsMatchEntity;

}
