package UZSL.dto.match;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClubsMatchInfoDTO {

    private String clubsMatchInfoId;
    private String homeTeamLogo;
    private String homeTeamGoalNumber;
    private String visitorTeamGoalNumber;
    private String visitorTeamLogo;
}
