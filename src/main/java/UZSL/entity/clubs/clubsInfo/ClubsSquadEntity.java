package UZSL.entity.clubs.clubsInfo;

import UZSL.entity.match.HomeTeamEntity;
import UZSL.entity.match.TeamsEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "clubs_squad")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClubsSquadEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String clubsSquadId;

    @Column(name = "clubs_full_name")
    private String clubsFullName;

    @OneToMany(mappedBy = "clubsSquadInGoalKeepers", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<GoalKeepersEntity> goalKeepersEntityList;

    @OneToMany(mappedBy = "clubsSquadInDefendersEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<DefendersEntity> defenderEntityList;

    @OneToMany(mappedBy = "clubsSquadInMidfielder", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<MidFieldersEntity> midFieldersEntityList;

    @OneToMany(mappedBy = "clubsSquadInStriker", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<StrikersEntity> strikersEntityList;

    @OneToOne(mappedBy = "clubsSquadProfile", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private ClubsProfileEntity clubsProfileEntity;

    @OneToOne(mappedBy = "clubsSquadEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private HomeTeamEntity teamsClubsSquad;

}
