package UZSL.domain.model.entity.stats;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "stats_goal")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatsPlayersEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String statsPlayerId;
    @Column(name = "title")
    private String title;
    @Column(name = "first_name_player")
    private String firstName;
    @Column(name = "last_name_player")
    private String lastName;
    @Column(name = "club_number_player")
    private String clubNumber;
    @Column(name = "club_full_name")
    private String clubsFullName;
    @Column(name = "goals")
    private int goals;
    @Column(name = "assist")
    private int assist;
    @Column(name = "shots")
    private int shots;
    @Column(name = "own_goal")
    private int ownGoal;
    @Column(name = "penalties")
    private int penalties;
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stats_entity_player_list_id")
    private StatsEntity statsEntity;
}
