package UZSL.dto.match.created;

import UZSL.dto.match.teams_logo.TeamsLogoCreatedDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HomeTeamCreatedDTO {

    private String homeTeamName;
    private int ownGoal;
    private int playedGames;
    private TeamsLogoCreatedDTO homeLogoDTO;
}
