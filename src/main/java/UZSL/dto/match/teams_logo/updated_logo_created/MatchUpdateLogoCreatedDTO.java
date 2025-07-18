package UZSL.dto.match.teams_logo.updated_logo_created;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MatchUpdateLogoCreatedDTO {

    private List<TeamsUpdateLogoCreatedDTO> teamsUpdateLogoCreatedDTOList;
}
