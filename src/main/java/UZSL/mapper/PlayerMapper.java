package UZSL.mapper;

import UZSL.dto.clubs.clubs_info.player.created.PlayerCreatedDTO;
import UZSL.dto.clubs.clubs_info.player.dto.PlayerCareerDTO;
import UZSL.dto.clubs.clubs_info.player.dto.PlayerDTO;
import UZSL.dto.clubs.clubs_info.player.dto.PlayerDetailDTO;
import UZSL.entity.clubs.clubsInfo.player_info.PlayerCareerEntity;
import UZSL.entity.clubs.clubsInfo.player_info.PlayerDetailEntity;
import UZSL.entity.clubs.clubsInfo.player_info.PlayerEntity;

public class PlayerMapper {

    /// DTO -> ENTITY
    public static PlayerDetailEntity toPlayerDetailEntity(PlayerCreatedDTO dto) {
        return PlayerDetailEntity.builder()
                .fouls(dto.getPlayerDetailCreated().getFouls())
                .yellowCards(dto.getPlayerDetailCreated().getYellowCards())
                .appearances(dto.getPlayerDetailCreated().getAppearances())
                .sprints(dto.getPlayerDetailCreated().getSprints())
                .intensiveRuns(dto.getPlayerDetailCreated().getIntensiveRuns())
                .distanceKm(dto.getPlayerDetailCreated().getDistanceKm())
                .speedKmH(dto.getPlayerDetailCreated().getSpeedKmH())
                .crosses(dto.getPlayerDetailCreated().getCrosses())
                .build();
    }

    public static PlayerCareerEntity toPlayerCareerEntity(PlayerCreatedDTO dto) {
        return PlayerCareerEntity.builder()
                .appearances(dto.getPlayerCareerCreated().getAppearances())
                .goals(dto.getPlayerCareerCreated().getGoals())
                .assists(dto.getPlayerCareerCreated().getAssists())
                .ballActions(dto.getPlayerCareerCreated().getBallActions())
                .distanceKmForSeason(dto.getPlayerCareerCreated().getDistanceKmForSeason())
                .penalties(dto.getPlayerCareerCreated().getPenalties())
                .build();
    }

    /// ENTITY -> DTO
    public static PlayerDTO toDTO(PlayerEntity entity) {
        return PlayerDTO.builder()
                .playerId(entity.getPlayerId())
                .playerDetail(toDetailDTO(entity.getPlayerDetailEntity()))
                .playerCareer(toCareerDTO(entity.getPlayerCareerEntity()))
                .build();
    }

    public static PlayerDetailDTO toDetailDTO(PlayerDetailEntity detailEntity) {
        return PlayerDetailDTO.builder()
                .fouls(detailEntity.getFouls())
                .yellowCards(detailEntity.getYellowCards())
                .appearances(detailEntity.getAppearances())
                .sprints(detailEntity.getSprints())
                .intensiveRuns(detailEntity.getIntensiveRuns())
                .distanceKm(detailEntity.getDistanceKm())
                .speedKmH(detailEntity.getSpeedKmH())
                .crosses(detailEntity.getCrosses())
                .build();
    }

    public static PlayerCareerDTO toCareerDTO(PlayerCareerEntity careerEntity) {
        return PlayerCareerDTO.builder()
                .appearances(careerEntity.getAppearances())
                .goals(careerEntity.getGoals())
                .assists(careerEntity.getAssists())
                .ballActions(careerEntity.getBallActions())
                .distanceKmForSeason(careerEntity.getDistanceKmForSeason())
                .penalties(careerEntity.getPenalties())
                .build();
    }
}
