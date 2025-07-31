package UZSL.domain.model.entity.clubs.table;

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

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "clubs_table_home_entity")
    private ClubsTableHomeEntity clubsTableHomeEntity;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "clubs_table_away_entity")
    private ClubsTableAwayEntity clubsTableAwayEntity;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}
