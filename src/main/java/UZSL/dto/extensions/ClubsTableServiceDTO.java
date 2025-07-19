package UZSL.dto.extensions;

import UZSL.dto.table.ClubsTableDTO;
import UZSL.entity.table.ClubsTableEntity;
import UZSL.repository.table.ClubsTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClubsTableServiceDTO {
    private final ClubsTableRepository clubsTableRepository;

    @Autowired
    public ClubsTableServiceDTO(ClubsTableRepository clubsTableRepository) {
        this.clubsTableRepository = clubsTableRepository;
    }

    public ClubsTableDTO toDTO(ClubsTableEntity clubsTableEntity) {
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
