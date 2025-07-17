package UZSL.dto.match.updateDTO;

import UZSL.dto.match.teams_logo.TeamsLogoDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AwayTeamUpdatedDTO {

    private String awayTeamId;
    private int awayGoal;
    private TeamsLogoDTO awayTeamsLogo;
}
