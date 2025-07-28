package UZSL.entity.clubs.clubsInfo;

import UZSL.entity.clubs.clubsInfo.player_info.PlayerCareerEntity;
import UZSL.entity.clubs.clubsInfo.player_info.PlayerDetailEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "clubs_goalkeepers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GoalKeepersEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String clubsGoalKeepersId;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "club_number")
    private String clubNumber;
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goal_keepers")
    private ClubsSquadEntity clubsSquadInGoalKeepers;

    @OneToOne(mappedBy = "goalKeepersPlayer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private PlayerDetailEntity playerDetailGoalKeeper;

    @OneToOne(mappedBy = "goalKeepersCareer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private PlayerCareerEntity playerCareerGoalKeeper;
}
