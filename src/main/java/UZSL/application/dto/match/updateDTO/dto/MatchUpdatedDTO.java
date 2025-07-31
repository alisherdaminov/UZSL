package UZSL.application.dto.match.updateDTO.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MatchUpdatedDTO {

    private String matchUpdatedId;
    private List<TeamsUpdatedDTO> teamsUpdatedList;
}
