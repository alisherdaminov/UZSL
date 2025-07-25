package UZSL.dto.clubs.match_info;

import UZSL.dto.clubs.match_info.away_club.ClubsTableAwayDTO;
import UZSL.dto.clubs.match_info.home_club.ClubsTableHomeDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClubsTableDTO {

    private String clubsTableId;
    private ClubsTableHomeDTO clubsTableHome;
    private ClubsTableAwayDTO clubsTableAway;


}
