package UZSL.entity.clubs.clubsInfo;

import UZSL.entity.clubs.clubsInfo.player_info.PlayerCareerEntity;
import UZSL.entity.clubs.clubsInfo.player_info.PlayerDetailEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "clubs_midfielders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MidFieldersEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String clubsMidFieldersId;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "club_number")
    private String clubNumber;
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "midfielders")
    private ClubsSquadEntity clubsSquadInMidfielder;

    @OneToOne(mappedBy = "midFieldersPlayer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private PlayerDetailEntity playerDetailMidfielder;

    @OneToOne(mappedBy = "midFieldersCareer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private PlayerCareerEntity playerCareerMidfielder;
}
