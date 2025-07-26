package UZSL.service.clubs.info;

import UZSL.config.util.SpringSecurityUtil;
import UZSL.dto.clubs.clubs_info.created.*;
import UZSL.dto.clubs.clubs_info.dto.*;
import UZSL.dto.extensions.ClubsSquadServiceDTO;
import UZSL.entity.clubs.clubsInfo.*;
import UZSL.entity.match.HomeTeamEntity;
import UZSL.enums.UzSlRoles;
import UZSL.exception.AppBadException;
import UZSL.repository.clubs.clubsInfo.*;
import UZSL.repository.match.HomeTeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClubsInfoServiceImpl implements ClubsInfoService {

    @Autowired
    private ClubsSquadRepository clubsSquadRepository;
    @Autowired
    private HomeTeamRepository homeTeamRepository;
    @Autowired
    private ClubsSquadServiceDTO clubsSquadServiceDTO;

    /// CREATE SQUAD AND PROFILE
    @Override
    public ClubsSquadDTO createSquadAndProfile(String clubsId, ClubsSquadCreatedDTO squadCreatedDTO) {
        if (!SpringSecurityUtil.hasRole(UzSlRoles.ROLE_ADMIN)) {
            throw new AppBadException("User is not found!");
        }

        HomeTeamEntity homeTeam = homeTeamRepository.findById(clubsId).orElseThrow(() -> new AppBadException("Club's id: " + clubsId + " is not found!"));
        // 1. Create main squad entity
        ClubsSquadEntity clubsSquad = new ClubsSquadEntity();
        clubsSquad.setClubsFullName(squadCreatedDTO.getClubsFullName());
        clubsSquad.setTeamsClubsSquad(homeTeam);

        // Save all in one cascade operation
        ClubsSquadEntity savedSquad = clubsSquadRepository.save(clubsSquad);

        // 2. Goalkeepers
        if (squadCreatedDTO.getGoalKeepers() != null) {
            List<GoalKeepersEntity> goalKeepers = squadCreatedDTO.getGoalKeepers().stream()
                    .map(dto -> {
                        GoalKeepersEntity entity = clubsSquadServiceDTO.toGoalKeeperEntity(dto, savedSquad);
                        entity.setClubsSquadInGoalKeepers(savedSquad);
                        return entity;
                    }).collect(Collectors.toList());
            clubsSquad.setGoalKeepersEntityList(goalKeepers);
        }

        // 3. Defenders
        if (squadCreatedDTO.getDefenders() != null) {
            List<DefendersEntity> defenders = squadCreatedDTO.getDefenders().stream()
                    .map(dto -> {
                        DefendersEntity entity = clubsSquadServiceDTO.toDefendersEntity(dto, savedSquad);
                        entity.setClubsSquadInDefendersEntity(savedSquad);
                        return entity;
                    }).collect(Collectors.toList());
            clubsSquad.setDefenderEntityList(defenders);
        }

        // 4. Midfielders
        if (squadCreatedDTO.getMidFielders() != null) {
            List<MidFieldersEntity> midfielders = squadCreatedDTO.getMidFielders().stream()
                    .map(dto -> {
                        MidFieldersEntity entity = clubsSquadServiceDTO.toMidfieldersEntity(dto, savedSquad);
                        entity.setClubsSquadInMidfielder(savedSquad);
                        return entity;
                    }).collect(Collectors.toList());
            clubsSquad.setMidFieldersEntityList(midfielders);
        }

        // 5. Strikers
        if (squadCreatedDTO.getStrikers() != null) {
            List<StrikersEntity> strikers = squadCreatedDTO.getStrikers().stream()
                    .map(dto -> {
                        StrikersEntity entity = clubsSquadServiceDTO.toStrikersEntity(dto, savedSquad);
                        entity.setClubsSquadInStriker(savedSquad);
                        return entity;
                    }).collect(Collectors.toList());
            clubsSquad.setStrikersEntityList(strikers);
        }

        // 6. Profile
        ClubsProfileEntity profile = clubsSquadServiceDTO.getClubsProfileEntity(squadCreatedDTO, savedSquad);
        profile.setClubsSquadProfile(savedSquad);
        clubsSquad.setClubsProfileEntity(profile);

        // 8. Convert to DTO
        return clubsSquadServiceDTO.toClubsSquadDTO(savedSquad);
    }

    /// GET CLUBS ALL DATA IN LIST
    @Override
    public List<ClubsSquadDTO> getClubsInfoList() {
        List<ClubsSquadDTO> dtoList = clubsSquadRepository.findAll().stream().map(clubsSquadServiceDTO::toClubsSquadDTO).collect(Collectors.toList());
        Collections.reverse(dtoList);
        return dtoList;
    }

    /// UPDATE CLUBS SQUAD
    @Override
    public ClubsSquadDTO updateSquad(String clubsId, ClubsSquadCreatedDTO squadCreatedDTO) {
        Optional<ClubsSquadEntity> optionalClubsSquad = clubsSquadRepository.findById(clubsId);
        if (optionalClubsSquad.isEmpty()) {
            throw new AppBadException("Squad id: " + clubsId + " is not found!");
        }
        //TeamsEntity id is taking and checking for creation of clubs info
        HomeTeamEntity optionalTeams = homeTeamRepository.findById(clubsId).orElseThrow(() -> new AppBadException("Not found!"));

        //Parent entity
        ClubsSquadEntity entity = optionalClubsSquad.get();
        entity.setClubsFullName(squadCreatedDTO.getClubsFullName());
        entity.setTeamsClubsSquad(optionalTeams); // PARENT LINK
        ClubsSquadEntity saved = clubsSquadRepository.save(entity);

        //update Goal keepers
        List<GoalKeepersEntity> goalKeepersEntityList = new ArrayList<>();
        if (squadCreatedDTO.getGoalKeepers() != null) {
            goalKeepersEntityList = squadCreatedDTO.getGoalKeepers().stream().map(
                    goalKeepersDTO -> clubsSquadServiceDTO.toUpdateGoalKeeperEntity(goalKeepersDTO, saved)).collect(Collectors.toList());
        }
        entity.setGoalKeepersEntityList(goalKeepersEntityList);

        //update Defenders
        List<DefendersEntity> defendersEntityList = new ArrayList<>();
        if (squadCreatedDTO.getDefenders() != null) {
            defendersEntityList = squadCreatedDTO.getDefenders().stream().map(
                    defenders -> clubsSquadServiceDTO.toUpdateDefendersEntity(defenders, saved)).collect(Collectors.toList());
        }
        entity.setDefenderEntityList(defendersEntityList);

        //update Midfielders
        List<MidFieldersEntity> midFieldersEntityList = new ArrayList<>();
        if (squadCreatedDTO.getMidFielders() != null) {
            midFieldersEntityList = squadCreatedDTO.getMidFielders().stream().map(
                    midFielders -> clubsSquadServiceDTO.toUpdateMidfieldersEntity(midFielders, saved)).collect(Collectors.toList());
        }
        entity.setMidFieldersEntityList(midFieldersEntityList);

        //update Strikers
        List<StrikersEntity> strikersEntityList = new ArrayList<>();
        if (squadCreatedDTO.getStrikers() != null) {
            strikersEntityList = squadCreatedDTO.getStrikers().stream().map(
                    strikers -> clubsSquadServiceDTO.toUpdateStrikersEntity(strikers, saved)).collect(Collectors.toList());
        }
        entity.setStrikersEntityList(strikersEntityList);
        //update clubs profile
        clubsSquadServiceDTO.updateClubsProfileEntity(squadCreatedDTO, saved);
        return clubsSquadServiceDTO.toClubsSquadDTO(entity);
    }

    /// DELETE CLUBS SQUAD
    @Override
    public String deleteSquad(String clubsId) {
        clubsSquadRepository.deleteById(clubsId);
        return "";
    }
}
