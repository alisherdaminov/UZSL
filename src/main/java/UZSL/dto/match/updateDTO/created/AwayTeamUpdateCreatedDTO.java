package UZSL.dto.match.updateDTO.created;

import UZSL.dto.match.teams_logo.TeamsLogoCreatedDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AwayTeamUpdateCreatedDTO {

    private int awayGoal;
    private TeamsLogoCreatedDTO awayTeamsLogoDTO;
}
