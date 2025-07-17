package UZSL.entity.match;

import UZSL.entity.auth.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Table(name = "matches")
@Entity
@Getter
@Setter
public class MatchEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String matchId;
    @Column(name = "match_started_date")
    private String matchStartedDate;
    @Column(name = "match_started_time")
    private String matchStartedTime;
    @Column(name = "champions_league")
    private String championsLeague;
    @Column(name = "afc_cup")
    private String afcCup;
    @Column(name = "conference_league")
    private String conferenceLeague;
    @Column(name = "play_off")
    private String playOff;
    @Column(name = "relegation")
    private String relegation;
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
