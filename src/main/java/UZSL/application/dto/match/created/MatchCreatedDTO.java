package UZSL.application.dto.match.created;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MatchCreatedDTO {

    private String matchStartedDate;
    private String matchStartedTime;
    private List<TeamsCreatedDTO> teamsCreatedList;
}
