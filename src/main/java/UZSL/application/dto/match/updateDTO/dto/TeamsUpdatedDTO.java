package UZSL.application.dto.match.updateDTO.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TeamsUpdatedDTO {
    private String teamsId;
    private HomeTeamUpdatedDTO homeTeam;
    private AwayTeamUpdatedDTO awayTeam;

}
