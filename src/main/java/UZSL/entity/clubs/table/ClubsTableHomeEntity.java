package UZSL.entity.clubs.table;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "clubs_table_home")
@Getter
@Setter
public class ClubsTableHomeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String clubsTableHomeId;
    @Column(name = "home_club_name")
    private String homeClubName;
    @Column(name = "played_games_home")
    private int playedGames;
    @Column(name = "won_home")
    private int won;
    @Column(name = "drawn_home")
    private int drawn;
    @Column(name = "lost_home")
    private int lost;
    @Column(name = "total_points_home")
    private int totalPoints;
    @Column(name = "goals_own_home")
    private int goalsOwn;
    @Column(name = "goals_against_home")
    private int goalsAgainst;
    @Column(name = "created_at_home")
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToOne(mappedBy = "clubsTableHomeEntity", fetch = FetchType.LAZY)
    private ClubsTableEntity clubsTableEntity;

}
