package UZSL.entity.clubs.clubsInfo;

import UZSL.entity.clubs.clubsInfo.player_info.PlayerCareerEntity;
import UZSL.entity.clubs.clubsInfo.player_info.PlayerDetailEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "clubs_strikers")
@Getter
@Setter
public class StrikersEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String clubsStrikersId;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "club_number")
    private String clubNumber;
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "strikers")
    private ClubsSquadEntity clubsSquadInStriker;

    @OneToOne(mappedBy = "strikersPlayer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private PlayerDetailEntity playerDetailStriker;

    @OneToOne(mappedBy = "strikersCareer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private PlayerCareerEntity playerCareerStriker;
}
