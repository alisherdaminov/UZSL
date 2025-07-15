package UZSL.dto.match.created;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MatchCreatedDTO {

    private String matchStartedDate;
    private String matchStartedTime;
    private String championsLeague;
    private String afcCup;
    private String conferenceLeague;
    private String playOff;
    private String relegation;
    private List<TeamsCreatedDTO> teamsCreatedList;
}
