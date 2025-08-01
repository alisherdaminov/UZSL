package UZSL.application.dto.match.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MatchDTO {

    private String matchId;
    private String matchStartedDate;
    private String matchStartedTime;
    private List<TeamsDTO> teamsList;
}
