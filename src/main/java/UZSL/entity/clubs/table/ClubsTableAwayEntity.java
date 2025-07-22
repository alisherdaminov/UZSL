package UZSL.entity.clubs.table;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "clubs_table_away")
@Getter
@Setter
public class ClubsTableAwayEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String clubsTableAwayId;
    @Column(name = "away_club_name")
    private String awayClubName;
    @Column(name = "played_games_away")
    private int playedGames;
    @Column(name = "won_away")
    private int won;
    @Column(name = "drawn_away")
    private int drawn;
    @Column(name = "lost_away")
    private int lost;
    @Column(name = "total_points_away")
    private int totalPoints;
    @Column(name = "goals_own_away")
    private int goalsOwn;
    @Column(name = "goals_against_away")
    private int goalsAgainst;
    @Column(name = "created_at_away")
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToOne(mappedBy = "clubsTableAwayEntity", fetch = FetchType.LAZY)
    private ClubsTableEntity clubsTableEntity;
}
