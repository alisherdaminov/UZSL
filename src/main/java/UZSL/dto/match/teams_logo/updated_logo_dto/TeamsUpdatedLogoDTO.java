package UZSL.dto.match.teams_logo.updated_logo_dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TeamsUpdatedLogoDTO {
    private String teamsId;
    private HomeTeamUpdatedLogoDTO homeTeam;
    private AwayTeamUpdatedLogoDTO awayTeam;

}
