package UZSL.domain.model.entity.match;

import UZSL.domain.model.entity.auth.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Table(name = "matches")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatchEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String matchId;
    @Column(name = "match_started_date")
    private String matchStartedDate;
    @Column(name = "match_started_time")
    private String matchStartedTime;
    @Column(name = "isProcessed")
    private boolean isProcessed;

    // uz_sl_admin
    @Column(name = "user_id")
    private Integer userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private UserEntity userEntity;

    //Teams list linked
    @OneToMany(mappedBy = "teamsMatchEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<TeamsEntity> teamsEntityList;


}
