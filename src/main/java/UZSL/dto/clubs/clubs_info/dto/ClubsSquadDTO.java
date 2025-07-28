package UZSL.dto.clubs.clubs_info.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClubsSquadDTO {

    private String clubsSquadId;
    private String clubsFullName;
    private List<GoalKeepersDTO> goalKeepers;
    private List<DefendersDTO> defenders;
    private List<MidFieldersDTO> midFielders;
    private List<StrikersDTO> strikers;
    private ClubsProfileDTO clubsProfile;

}
