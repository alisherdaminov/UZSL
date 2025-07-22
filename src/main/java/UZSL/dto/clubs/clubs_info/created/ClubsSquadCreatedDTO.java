package UZSL.dto.clubs.clubs_info.created;

import UZSL.dto.clubs.clubs_info.dto.ClubsProfileDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ClubsSquadCreatedDTO {

    private String clubsFullName;
    private List<GoalKeepersCreatedDTO> goalKeepers;
    private List<DefendersCreatedDTO> defenders;
    private List<MidFieldersCreatedDTO> midFielders;
    private List<StrikersCreatedDTO> strikers;
    private ClubsProfileDTO clubsProfile;
}
