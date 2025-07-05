package UZSL.dto.match.created;

import UZSL.dto.match.image.MatchLogoCreatedDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClubsMatchInfoCreatedDTO {

    private String homeTeamName;
    private String homeTeamGoalNumber;
    private String visitorTeamGoalNumber;
    private String visitorTeamName;
    private MatchLogoCreatedDTO homeTeamLogo;
    private MatchLogoCreatedDTO visitorTeamLogo;
}
