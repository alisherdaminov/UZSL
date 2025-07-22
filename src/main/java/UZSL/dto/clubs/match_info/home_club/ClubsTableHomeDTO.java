package UZSL.dto.clubs.match_info.home_club;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClubsTableHomeDTO {

    private String clubsTableHomeId;
    private String homeClubName;
    private int playedGames;
    private int won;
    private int drawn;
    private int lost;
    private int totalPoints;
    private int goalsOwn;
    private int goalsAgainst;
}
