package UZSL.dto.extensions;

import UZSL.dto.clubs.clubs_info.player.dto.PlayerCareerDTO;
import UZSL.dto.clubs.clubs_info.player.dto.PlayerDTO;
import UZSL.dto.clubs.clubs_info.player.dto.PlayerDetailDTO;
import UZSL.entity.clubs.clubsInfo.player_info.PlayerEntity;
import org.springframework.stereotype.Component;

@Component
public class PlayerServiceDTO {

    public PlayerDTO toPlayerDTO(PlayerEntity playerEntity) {
        PlayerDetailDTO playerDetailDTO = new PlayerDetailDTO();
        playerDetailDTO.setFouls(playerEntity.getPlayerDetailEntity().getFouls());
        playerDetailDTO.setYellowCards(playerEntity.getPlayerDetailEntity().getYellowCards());
        playerDetailDTO.setAppearances(playerEntity.getPlayerDetailEntity().getAppearances());
        playerDetailDTO.setSprints(playerEntity.getPlayerDetailEntity().getSprints());
        playerDetailDTO.setIntensiveRuns(playerEntity.getPlayerDetailEntity().getIntensiveRuns());
        playerDetailDTO.setDistanceKm(playerEntity.getPlayerDetailEntity().getDistanceKm());
        playerDetailDTO.setSpeedKmH(playerEntity.getPlayerDetailEntity().getSpeedKmH());
        playerDetailDTO.setCrosses(playerEntity.getPlayerDetailEntity().getCrosses());
        return getPlayerDTO(playerEntity, playerDetailDTO);
    }

    private  PlayerDTO getPlayerDTO(PlayerEntity playerEntity, PlayerDetailDTO playerDetailDTO) {
        PlayerCareerDTO playerCareerDTO = new PlayerCareerDTO();
        playerCareerDTO.setAppearances(playerEntity.getPlayerCareerEntity().getAppearances());
        playerCareerDTO.setGoals(playerEntity.getPlayerCareerEntity().getGoals());
        playerCareerDTO.setAssists(playerEntity.getPlayerCareerEntity().getAssists());
        playerCareerDTO.setBallActions(playerEntity.getPlayerCareerEntity().getBallActions());
        playerCareerDTO.setDistanceKmForSeason(playerEntity.getPlayerCareerEntity().getDistanceKmForSeason());
        playerCareerDTO.setPenalties(playerEntity.getPlayerCareerEntity().getPenalties());

        PlayerDTO playerDTO = new PlayerDTO();
        playerDTO.setPlayerDetail(playerDetailDTO);
        playerDTO.setPlayerCareer(playerCareerDTO);
        return playerDTO;
    }

}
