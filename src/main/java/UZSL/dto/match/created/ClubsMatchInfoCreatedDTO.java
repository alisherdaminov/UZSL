package UZSL.dto.match.created;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClubsMatchInfoCreatedDTO {

    private String homeTeamName;
    private String homeTeamLogo;
    private String homeTeamGoalNumber;
    private String visitorTeamGoalNumber;
    private String visitorTeamLogo;
    private String visitorTeamName;
}
