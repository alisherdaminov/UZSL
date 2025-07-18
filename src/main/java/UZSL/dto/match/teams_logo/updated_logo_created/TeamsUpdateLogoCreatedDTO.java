package UZSL.dto.match.teams_logo.updated_logo_created;

import UZSL.dto.match.updateDTO.created.AwayTeamUpdateCreatedDTO;
import UZSL.dto.match.updateDTO.created.HomeTeamUpdateCreatedDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamsUpdateLogoCreatedDTO {

    private HomeTeamUpdateLogoCreatedDTO homeTeamUpdateLogoCreatedDTO;
    private AwayTeamUpdateLogoCreatedDTO awayTeamUpdateLogoCreatedDTO;
}
