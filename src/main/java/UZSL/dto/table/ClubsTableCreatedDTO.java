package UZSL.dto.table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClubsTableCreatedDTO {

    private String homeClubName;
    private String visitorClubName;
    private int playedGames;
    private int won;
    private int drawn;
    private int lost;
    private int goalsOwn;
    private int goalsAgainst;


}
