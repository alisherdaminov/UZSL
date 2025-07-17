package UZSL.dto.match.updateDTO;

import UZSL.dto.match.AwayTeamDTO;
import UZSL.dto.match.HomeTeamDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TeamsUpdatedDTO {
    private String teamsId;
    private HomeTeamUpdatedDTO homeTeam;
    private AwayTeamUpdatedDTO awayTeam;

}
