package UZSL.domain.model.entity.clubs.clubsInfo;

import UZSL.domain.model.entity.clubs.clubsInfo.player_info.PlayerCareerEntity;
import UZSL.domain.model.entity.clubs.clubsInfo.player_info.PlayerDetailEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "clubs_defenders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DefendersEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String clubsDefendersId;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "club_number")
    private String clubNumber;
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "defenders_list")
    private ClubsSquadEntity clubsSquadInDefendersEntity;

    @OneToOne(mappedBy = "defendersPlayer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private PlayerDetailEntity playerDetailDefender;

    @OneToOne(mappedBy = "defendersCareer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private PlayerCareerEntity playerCareerDefender;
}
