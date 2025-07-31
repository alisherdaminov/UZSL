package UZSL.application.dto.clubs.match_info;

import UZSL.application.dto.clubs.match_info.away_club.ClubsTableAwayDTO;
import UZSL.application.dto.clubs.match_info.home_club.ClubsTableHomeDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClubsTableDTO {

    private String clubsTableId;
    private ClubsTableHomeDTO clubsTableHome;
    private ClubsTableAwayDTO clubsTableAway;


}
