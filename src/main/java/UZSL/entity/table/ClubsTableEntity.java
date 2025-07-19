package UZSL.entity.table;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "clubs_table")
@Getter
@Setter
public class ClubsTableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String clubsTableId;
    @Column(name = "home_club_name")
    private String homeClubName;
    @Column(name = "visitor_club_name")
    private String visitorClubName;
    @Column(name = "played_games")
    private int playedGames;
    @Column(name = "won")
    private int won;
    @Column(name = "drawn")
    private int drawn;
    @Column(name = "lost")
    private int lost;
    @Column(name = "total_points")
    private int totalPoints;
    @Column(name = "goals_own")
    private int goalsOwn;
    @Column(name = "goals_against")
    private int goalsAgainst;
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

//    // uz_sl_admin
//    @Column(name = "user_id")
//    private Integer userId;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id", insertable = false, updatable = false)
//    private UserEntity userEntity;


}
