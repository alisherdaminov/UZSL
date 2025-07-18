package UZSL.dto.match.teams_logo.updated_logo_dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MatchUpdatedLogoDTO {

    private String matchUpdatedId;
    private List<TeamsUpdatedLogoDTO> teamsUpdatedList;
}
