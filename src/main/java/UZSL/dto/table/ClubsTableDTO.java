package UZSL.dto.table;

import UZSL.entity.table.ClubsTableEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClubsTableDTO {

    private String clubsTableId;
    private String homeClubName;
    private String visitorClubName;
    private int playedGames;
    private int won;
    private int drawn;
    private int lost;
    private int totalPoints;
    private int goalsOwn;
    private int goalsAgainst;

    public static ClubsTableDTO toDTO(ClubsTableEntity clubsTableEntity) {
        ClubsTableDTO dto = new ClubsTableDTO();
        dto.setHomeClubName(clubsTableEntity.getHomeClubName());
        dto.setVisitorClubName(clubsTableEntity.getVisitorClubName());
        dto.setPlayedGames(clubsTableEntity.getPlayedGames());
        dto.setWon(clubsTableEntity.getWon());
        dto.setDrawn(clubsTableEntity.getDrawn());
        dto.setLost(clubsTableEntity.getLost());
        dto.setTotalPoints(clubsTableEntity.getTotalPoints());
        dto.setGoalsOwn(clubsTableEntity.getGoalsOwn());
        dto.setGoalsAgainst(clubsTableEntity.getGoalsAgainst());
        return dto;
    }
}
