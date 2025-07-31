package UZSL.application.dto.match.created;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamsCreatedDTO {

    private HomeTeamCreatedDTO homeTeam;
    private AwayTeamCreatedDTO awayTeam;
}
