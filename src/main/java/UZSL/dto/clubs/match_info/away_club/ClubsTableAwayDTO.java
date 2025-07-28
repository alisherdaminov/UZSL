package UZSL.dto.clubs.match_info.away_club;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClubsTableAwayDTO {

    private String clubsTableAwayId;
    private String awayClubName;
    private int playedGames;
    private int won;
    private int drawn;
    private int lost;
    private int totalPoints;
    private int goalsOwn;
    private int goalsAgainst;
}
