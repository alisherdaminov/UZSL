package UZSL.dto.match.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MatchDTO {

    private String matchId;
    private String matchStartedDate;
    private String matchStartedTime;
    private List<TeamsDTO> teamsList;
}
