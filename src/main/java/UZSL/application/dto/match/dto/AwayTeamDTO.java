package UZSL.application.dto.match.dto;

import UZSL.application.dto.match.teams_logo.TeamsLogoDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AwayTeamDTO {

    private String awayTeamId;
    private String awayTeamName;
    private TeamsLogoDTO awayTeamsLogo;
}
