package UZSL.dto.extensions;

import UZSL.dto.clubs.match_info.ClubsTableDTO;
import UZSL.dto.clubs.match_info.away_club.ClubsTableAwayDTO;
import UZSL.dto.clubs.match_info.home_club.ClubsTableHomeDTO;
import UZSL.entity.clubs.table.ClubsTableEntity;
import UZSL.repository.clubs.table.ClubsTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClubsTableServiceDTO {
    @Autowired
    private ClubsTableRepository clubsTableRepository;


    public ClubsTableDTO toDTO(ClubsTableEntity clubsTableEntity) {
        ClubsTableHomeDTO clubsTableHomeDTO = new ClubsTableHomeDTO();
        clubsTableHomeDTO.setHomeClubName(clubsTableEntity.getClubsTableHomeEntity().getHomeClubName());
        clubsTableHomeDTO.setPlayedGames(clubsTableEntity.getClubsTableHomeEntity().getPlayedGames());
        clubsTableHomeDTO.setWon(clubsTableEntity.getClubsTableHomeEntity().getWon());
        clubsTableHomeDTO.setDrawn(clubsTableEntity.getClubsTableHomeEntity().getDrawn());
        clubsTableHomeDTO.setLost(clubsTableEntity.getClubsTableHomeEntity().getLost());
        clubsTableHomeDTO.setTotalPoints(clubsTableEntity.getClubsTableHomeEntity().getTotalPoints());
        clubsTableHomeDTO.setGoalsOwn(clubsTableEntity.getClubsTableHomeEntity().getGoalsOwn());
        clubsTableHomeDTO.setGoalsAgainst(clubsTableEntity.getClubsTableHomeEntity().getGoalsAgainst());

        ClubsTableDTO clubsTableDTO = getClubsTableDTO(clubsTableEntity, clubsTableHomeDTO);
        return clubsTableDTO;
    }

    private static ClubsTableDTO getClubsTableDTO(ClubsTableEntity clubsTableEntity, ClubsTableHomeDTO clubsTableHomeDTO) {
        ClubsTableAwayDTO clubsTableAwayDTO = new ClubsTableAwayDTO();
        clubsTableAwayDTO.setAwayClubName(clubsTableEntity.getClubsTableAwayEntity().getAwayClubName());
        clubsTableAwayDTO.setPlayedGames(clubsTableEntity.getClubsTableAwayEntity().getPlayedGames());
        clubsTableAwayDTO.setWon(clubsTableEntity.getClubsTableAwayEntity().getWon());
        clubsTableAwayDTO.setDrawn(clubsTableEntity.getClubsTableAwayEntity().getDrawn());
        clubsTableAwayDTO.setLost(clubsTableEntity.getClubsTableAwayEntity().getLost());
        clubsTableAwayDTO.setTotalPoints(clubsTableEntity.getClubsTableAwayEntity().getTotalPoints());
        clubsTableAwayDTO.setGoalsOwn(clubsTableEntity.getClubsTableAwayEntity().getGoalsOwn());
        clubsTableAwayDTO.setGoalsAgainst(clubsTableEntity.getClubsTableAwayEntity().getGoalsAgainst());

        ClubsTableDTO clubsTableDTO = new ClubsTableDTO();
        clubsTableDTO.setClubsTableHome(clubsTableHomeDTO);
        clubsTableDTO.setClubsTableAway(clubsTableAwayDTO);
        return clubsTableDTO;
    }
}
