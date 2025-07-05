package UZSL.entity.match;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "clubs_match_info")
public class ClubsMatchInfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String clubsMatchInfoId;
    @Column(name = "home_team_name")
    private String homeTeamName;
//    @Column(name = "home_team_logo")
//    private String homeTeamLogo;
    @Column(name = "home_team_goal_number")
    private String homeTeamGoalNumber;
    @Column(name = "visitor_team_goal_number")
    private String visitorTeamGoalNumber;
//    @Column(name = "visitor_team_logo")
//    private String visitorTeamLogo;
    @Column(name = "visitor_team_name")
    private String visitorTeamName;

    @Column(name = "home_logo_id")
    private String homeLogoId;

    @Column(name = "visitor_logo_id")
    private String visitorLogoId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id")
    private MatchEntity matchEntity;
}
