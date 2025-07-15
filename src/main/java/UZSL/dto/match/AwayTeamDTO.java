package UZSL.dto.match;

import UZSL.dto.match.teams_logo.TeamsLogoDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AwayTeamDTO {

    private String awayTeamId;
    private String awayTeamName;
    private int awayGoal;
    private int playedGames;
    private TeamsLogoDTO awayTeamsLogo;
}
