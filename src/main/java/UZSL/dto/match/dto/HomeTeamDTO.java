package UZSL.dto.match.dto;

import UZSL.dto.match.teams_logo.TeamsLogoDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HomeTeamDTO {

    private String homeTeamId;
    private String homeTeamName;
    private TeamsLogoDTO homeTeamsLogo;
}
