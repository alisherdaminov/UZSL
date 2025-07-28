package UZSL.mapper;

import UZSL.dto.clubs.match_info.ClubsTableDTO;
import UZSL.dto.clubs.match_info.away_club.ClubsTableAwayDTO;
import UZSL.dto.clubs.match_info.home_club.ClubsTableHomeDTO;
import UZSL.entity.clubs.table.ClubsTableEntity;
import UZSL.repository.clubs.table.ClubsTableRepository;

public class ClubsTableMapper {

    private ClubsTableRepository clubsTableRepository;

    public ClubsTableDTO toClubsTableDTO(ClubsTableEntity entity) {
        return ClubsTableDTO.builder().
                clubsTableHome(toTableHomeDTO(entity)).
                clubsTableAway(toTableAwayDTO(entity)).
                build();
    }

    public static ClubsTableHomeDTO toTableHomeDTO(ClubsTableEntity clubsTableEntity) {
        return ClubsTableHomeDTO.builder().
                clubsTableHomeId(clubsTableEntity.getClubsTableHomeEntity().getClubsTableHomeId()).
                homeClubName(clubsTableEntity.getClubsTableHomeEntity().getHomeClubName()).
                playedGames(clubsTableEntity.getClubsTableHomeEntity().getPlayedGames()).
                won(clubsTableEntity.getClubsTableHomeEntity().getWon()).
                drawn(clubsTableEntity.getClubsTableHomeEntity().getDrawn()).
                lost(clubsTableEntity.getClubsTableHomeEntity().getLost()).
                totalPoints(clubsTableEntity.getClubsTableHomeEntity().getTotalPoints()).
                goalsOwn(clubsTableEntity.getClubsTableHomeEntity().getGoalsOwn()).
                goalsAgainst(clubsTableEntity.getClubsTableHomeEntity().getGoalsAgainst()).
                build();
    }

    public static ClubsTableAwayDTO toTableAwayDTO(ClubsTableEntity clubsTableEntity) {
        return ClubsTableAwayDTO.builder().
                clubsTableAwayId(clubsTableEntity.getClubsTableAwayEntity().getClubsTableAwayId()).
                awayClubName(clubsTableEntity.getClubsTableAwayEntity().getAwayClubName()).
                playedGames(clubsTableEntity.getClubsTableAwayEntity().getPlayedGames()).
                won(clubsTableEntity.getClubsTableAwayEntity().getWon()).
                drawn(clubsTableEntity.getClubsTableAwayEntity().getDrawn()).
                lost(clubsTableEntity.getClubsTableAwayEntity().getLost()).
                totalPoints(clubsTableEntity.getClubsTableAwayEntity().getTotalPoints()).
                goalsOwn(clubsTableEntity.getClubsTableAwayEntity().getGoalsOwn()).
                goalsAgainst(clubsTableEntity.getClubsTableAwayEntity().getGoalsAgainst()).
                build();
    }
}
