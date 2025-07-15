package UZSL.dto.match;

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
    private String championsLeague;
    private String afcCup;
    private String conferenceLeague;
    private String playOff;
    private String relegation;
    private List<TeamsDTO> teamsList;
}
