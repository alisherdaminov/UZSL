package UZSL.dto.match;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TeamsDTO {
    private String teamsId;
    private HomeTeamDTO homeTeam;
    private AwayTeamDTO awayTeam;

}
