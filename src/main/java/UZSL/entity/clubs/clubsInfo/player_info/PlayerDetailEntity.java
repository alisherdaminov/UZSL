package UZSL.entity.clubs.clubsInfo.player_info;

import UZSL.entity.clubs.clubsInfo.DefendersEntity;
import UZSL.entity.clubs.clubsInfo.GoalKeepersEntity;
import UZSL.entity.clubs.clubsInfo.MidFieldersEntity;
import UZSL.entity.clubs.clubsInfo.StrikersEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "clubs_player_detail")
@Getter
@Setter
public class PlayerDetailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String playerDetailId;
    @Column(name = "fouls")
    private String fouls;
    @Column(name = "yellow_cards")
    private String yellowCards;
    @Column(name = "appearances")
    private String appearances;
    @Column(name = "sprints")
    private String sprints;
    @Column(name = "intensive_runs")
    private String intensiveRuns;
    @Column(name = "distanceKm")
    private String distanceKm;
    @Column(name = "speedKmH")
    private String speedKmH;
    @Column(name = "crosses")
    private String crosses;
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "player_detail")
    private PlayerEntity playerEntity;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "player_goal_keeper_detail")
    private GoalKeepersEntity goalKeepersPlayer;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "player_defender_detail")
    private DefendersEntity defendersPlayer;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "player_midfielder_detail")
    private MidFieldersEntity midFieldersPlayer;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "player_striker_detail")
    private StrikersEntity strikersPlayer;

}
