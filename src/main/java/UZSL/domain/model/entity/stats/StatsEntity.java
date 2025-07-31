package UZSL.domain.model.entity.stats;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "stats")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String statsId;
    @Column(name = "stats_name")
    private String statsName;

    @OneToMany(mappedBy = "statsEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<StatsPlayersEntity> statsPlayersEntityList;
    
}
