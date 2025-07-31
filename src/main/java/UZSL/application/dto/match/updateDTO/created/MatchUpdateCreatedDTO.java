package UZSL.application.dto.match.updateDTO.created;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MatchUpdateCreatedDTO {

    private List<TeamsUpdateCreatedDTO> teamsUpdateCreatedDTOList;
}
