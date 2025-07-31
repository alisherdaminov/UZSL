package UZSL.application.dto.match.created;

import UZSL.application.dto.match.teams_logo.TeamsLogoCreatedDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AwayTeamCreatedDTO {

    private String awayTeamName;
   // private int awayGoal;
    //private int playedGames;
    private TeamsLogoCreatedDTO awayTeamsLogoDTO;
}
