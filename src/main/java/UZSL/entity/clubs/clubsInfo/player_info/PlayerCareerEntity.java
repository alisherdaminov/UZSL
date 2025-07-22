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
@Table(name = "clubs_player_career")
@Getter
@Setter
public class PlayerCareerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String playerDetailId;
    @Column(name = "appearances")
    private String appearances;
    @Column(name = "goals")
    private String goals;
    @Column(name = "assists")
    private String assists;
    @Column(name = "ballActions")
    private String ballActions;
    @Column(name = "distanceKmForSeason")
    private String distanceKmForSeason;
    @Column(name = "penalties")
    private String penalties;
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "player_career")
    private PlayerEntity playerCareer;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "player_goal_keeper_career")
    private GoalKeepersEntity goalKeepersCareer;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "player_defender_career")
    private DefendersEntity defendersCareer;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "player_midfielder_career")
    private MidFieldersEntity midFieldersCareer;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "player_striker_career")
    private StrikersEntity strikersCareer;

}
