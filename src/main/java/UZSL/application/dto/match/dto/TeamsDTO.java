package UZSL.application.dto.match.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TeamsDTO {
    private String teamsId;
    private HomeTeamDTO homeTeam;
    private AwayTeamDTO awayTeam;

}
