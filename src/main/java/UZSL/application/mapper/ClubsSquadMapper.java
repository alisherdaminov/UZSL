package UZSL.application.mapper;

import UZSL.application.dto.clubs.clubs_info.created.*;
import UZSL.application.dto.clubs.clubs_info.dto.*;
import UZSL.domain.model.entity.clubs.clubsInfo.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ClubsSquadMapper {

    /// ENTITY TO DTO
    public GoalKeepersEntity toGoalKeeperEntity(GoalKeepersCreatedDTO goalKeepersDTO) {
        return GoalKeepersEntity.builder().
                firstName(goalKeepersDTO.getFirstName()).
                lastName(goalKeepersDTO.getLastName()).
                clubNumber(goalKeepersDTO.getClubNumber()).
                createdAt(LocalDateTime.now()).
                build();
    }

    public DefendersEntity toDefendersEntity(DefendersCreatedDTO defenders) {
        return DefendersEntity.builder().
                firstName(defenders.getFirstName()).
                lastName(defenders.getLastName()).
                clubNumber(defenders.getClubNumber()).
                createdAt(LocalDateTime.now()).
                build();
    }

    public MidFieldersEntity toMidfieldersEntity(MidFieldersCreatedDTO midFielders) {
        return MidFieldersEntity.builder().
                firstName(midFielders.getFirstName()).
                lastName(midFielders.getLastName()).
                clubNumber(midFielders.getClubNumber()).
                createdAt(LocalDateTime.now()).
                build();
    }

    public StrikersEntity toStrikersEntity(StrikersCreatedDTO strikers) {
        return StrikersEntity.builder().
                firstName(strikers.getFirstName()).
                lastName(strikers.getLastName()).
                clubNumber(strikers.getClubNumber()).
                createdAt(LocalDateTime.now()).
                build();
    }

    public ClubsProfileEntity getClubsProfileEntity(ClubsSquadCreatedDTO squadCreatedDTO) {
        return ClubsProfileEntity.builder().
                stadiumName(squadCreatedDTO.getClubsProfile().getStadiumName()).
                founded(squadCreatedDTO.getClubsProfile().getFounded()).
                clubsColor(squadCreatedDTO.getClubsProfile().getClubsColor()).
                street(squadCreatedDTO.getClubsProfile().getStreet()).
                city(squadCreatedDTO.getClubsProfile().getCity()).
                direction(squadCreatedDTO.getClubsProfile().getDirection()).
                phone(squadCreatedDTO.getClubsProfile().getPhone()).
                fax(squadCreatedDTO.getClubsProfile().getFax()).
                websiteName(squadCreatedDTO.getClubsProfile().getWebsiteName()).
                emailName(squadCreatedDTO.getClubsProfile().getEmailName()).
                build();
    }

    /// UPDATE ENTITY TO DTO
    public GoalKeepersEntity toUpdateGoalKeeperEntity(GoalKeepersCreatedDTO goalKeepersDTO, String strikersId) {
        return GoalKeepersEntity.builder()
                .clubsGoalKeepersId(strikersId)
                .firstName(goalKeepersDTO.getFirstName())
                .lastName(goalKeepersDTO.getLastName())
                .clubNumber(goalKeepersDTO.getClubNumber())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public DefendersEntity toUpdateDefendersEntity(DefendersCreatedDTO defenders, String strikersId) {
        return DefendersEntity.builder()
                .clubsDefendersId(strikersId)
                .firstName(defenders.getFirstName())
                .lastName(defenders.getLastName())
                .clubNumber(defenders.getClubNumber())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public MidFieldersEntity toUpdateMidfieldersEntity(MidFieldersCreatedDTO midFielders, String strikersId) {
        return MidFieldersEntity.builder()
                .clubsMidFieldersId(strikersId)
                .firstName(midFielders.getFirstName())
                .lastName(midFielders.getLastName())
                .clubNumber(midFielders.getClubNumber())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public StrikersEntity toUpdateStrikersEntity(StrikersCreatedDTO dto, String strikersId) {
        return StrikersEntity.builder()
                .clubsStrikersId(strikersId)
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .clubNumber(dto.getClubNumber())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public ClubsProfileEntity toUpdateClubsProfileEntity(ClubsSquadCreatedDTO squadCreatedDTO, String clubsId) {
        return ClubsProfileEntity.builder().
                clubsProfileId(clubsId).
                stadiumName(squadCreatedDTO.getClubsProfile().getStadiumName()).
                founded(squadCreatedDTO.getClubsProfile().getFounded()).
                clubsColor(squadCreatedDTO.getClubsProfile().getClubsColor()).
                street(squadCreatedDTO.getClubsProfile().getStreet()).
                city(squadCreatedDTO.getClubsProfile().getCity()).
                direction(squadCreatedDTO.getClubsProfile().getDirection()).
                phone(squadCreatedDTO.getClubsProfile().getPhone()).
                fax(squadCreatedDTO.getClubsProfile().getFax()).
                websiteName(squadCreatedDTO.getClubsProfile().getWebsiteName()).
                emailName(squadCreatedDTO.getClubsProfile().getEmailName()).
                build();
    }

    /// DTO TO ENTITY
    public ClubsSquadDTO toClubsSquadDTO(ClubsSquadEntity entity) {
        ClubsSquadDTO clubsSquadDTO = new ClubsSquadDTO();
        clubsSquadDTO.setClubsSquadId(entity.getClubsSquadId());
        clubsSquadDTO.setClubsFullName(entity.getClubsFullName());

        //Goal keepers
        List<GoalKeepersDTO> goalKeepersDTOList = entity.getGoalKeepersEntityList().stream().map(
                dto -> GoalKeepersDTO.builder().
                        clubsGoalKeepersId(dto.getClubsGoalKeepersId()).
                        firstName(dto.getFirstName()).
                        lastName(dto.getLastName()).
                        clubNumber(dto.getClubNumber()).
                        build()
        ).collect(Collectors.toList());
        clubsSquadDTO.setGoalKeepers(goalKeepersDTOList);

        //Defenders
        List<DefendersDTO> defendersDTOList = entity.getDefenderEntityList().stream().map(
                dto -> DefendersDTO.builder().
                        clubsDefendersId(dto.getClubsDefendersId()).
                        firstName(dto.getFirstName()).
                        lastName(dto.getLastName()).
                        clubNumber(dto.getClubNumber()).
                        build()
        ).collect(Collectors.toList());
        clubsSquadDTO.setDefenders(defendersDTOList);

        //Midfielders
        List<MidFieldersDTO> midFieldersDTOList = entity.getMidFieldersEntityList().stream().map(
                dto -> MidFieldersDTO.builder().
                        clubsMidfieldersId(dto.getClubsMidFieldersId()).
                        firstName(dto.getFirstName()).
                        lastName(dto.getLastName()).
                        clubNumber(dto.getClubNumber()).
                        build()
        ).collect(Collectors.toList());
        clubsSquadDTO.setMidFielders(midFieldersDTOList);

        //Strikers
        List<StrikersDTO> strikersDTOList = entity.getStrikersEntityList().stream().map(
                dto -> StrikersDTO.builder().
                        clubsStrikersId(dto.getClubsStrikersId()).
                        firstName(dto.getFirstName()).
                        lastName(dto.getLastName()).
                        clubNumber(dto.getClubNumber()).
                        build()
        ).collect(Collectors.toList());
        clubsSquadDTO.setStrikers(strikersDTOList);


        // Clubs profile
        ClubsProfileDTO clubsProfileDTO = toClubsProfileDTO(entity);
        clubsSquadDTO.setClubsProfile(clubsProfileDTO);// PARENT LINK
        return clubsSquadDTO;
    }

    public ClubsProfileDTO toClubsProfileDTO(ClubsSquadEntity entity) {
        return ClubsProfileDTO.builder().
                clubsProfileId(entity.getClubsProfileEntity().getClubsProfileId()).
                stadiumName(entity.getClubsProfileEntity().getStadiumName()).
                founded(entity.getClubsProfileEntity().getFounded()).
                clubsColor(entity.getClubsProfileEntity().getClubsColor()).
                street(entity.getClubsProfileEntity().getStreet()).
                city(entity.getClubsProfileEntity().getCity()).
                direction(entity.getClubsProfileEntity().getDirection()).
                phone(entity.getClubsProfileEntity().getPhone()).
                fax(entity.getClubsProfileEntity().getFax()).
                websiteName(entity.getClubsProfileEntity().getWebsiteName()).
                emailName(entity.getClubsProfileEntity().getEmailName()).
                build();
    }
}
