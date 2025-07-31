package UZSL.domain.model.entity.clubs.clubsInfo.player_info;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "clubs_player")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String playerId;

    @OneToOne(mappedBy = "playerEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private PlayerDetailEntity playerDetailEntity;

    @OneToOne(mappedBy = "playerCareer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private PlayerCareerEntity playerCareerEntity;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

}
