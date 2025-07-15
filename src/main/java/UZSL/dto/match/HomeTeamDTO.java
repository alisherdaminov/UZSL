package UZSL.dto.match;

import UZSL.dto.match.teams_logo.TeamsLogoDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HomeTeamDTO {

    private String homeTeamId;
    private String homeTeamName;
    private int ownGoal;
    private int playedGames;
    private TeamsLogoDTO homeTeamsLogo;
}
