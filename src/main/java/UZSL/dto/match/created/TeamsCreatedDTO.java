package UZSL.dto.match.created;

import UZSL.dto.match.AwayTeamDTO;
import UZSL.dto.match.HomeTeamDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamsCreatedDTO {

    private HomeTeamCreatedDTO homeTeam;
    private AwayTeamCreatedDTO awayTeam;
}
