package UZSL.application.dto.match.teams_logo.updated_logo_dto;

import UZSL.application.dto.match.teams_logo.TeamsLogoDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HomeTeamUpdatedLogoDTO {

    private String homeTeamId;
   private TeamsLogoDTO homeTeamsLogo;
}
