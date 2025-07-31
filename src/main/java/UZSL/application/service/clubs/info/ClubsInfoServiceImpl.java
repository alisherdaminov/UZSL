package UZSL.application.service.clubs.info;

import UZSL.application.dto.clubs.clubs_info.created.ClubsSquadCreatedDTO;
import UZSL.application.dto.clubs.clubs_info.dto.ClubsSquadDTO;
import UZSL.domain.model.entity.clubs.clubsInfo.*;
import UZSL.domain.repository.clubs.clubsInfo.*;
import UZSL.domain.service.clubs.info.ClubsInfoService;
import UZSL.shared.util.SpringSecurityUtil;
import UZSL.application.mapper.ClubsSquadMapper;
import UZSL.domain.model.entity.match.HomeTeamEntity;
import UZSL.shared.enums.UzSlRoles;
import UZSL.shared.exception.AppBadException;
import UZSL.domain.repository.match.HomeTeamRepository;
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
    private GoalKeepersRepository goalKeepersRepository;
    @Autowired
    private DefendersRepository defendersRepository;
    @Autowired
    private MidfieldersRepository midfieldersRepository;
    @Autowired
    private StrikersRepository strikersRepository;
    @Autowired
    private ClubsProfileRepository clubsProfileRepository;
    @Autowired
    private ClubsSquadMapper clubsSquadMapper;

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
                        GoalKeepersEntity entity = clubsSquadMapper.toGoalKeeperEntity(dto);
                        entity.setClubsSquadInGoalKeepers(savedSquad);
                        goalKeepersRepository.save(entity);
                        return entity;
                    }).collect(Collectors.toList());
            clubsSquad.setGoalKeepersEntityList(goalKeepers);
        }

        // 3. Defenders
        if (squadCreatedDTO.getDefenders() != null) {
            List<DefendersEntity> defenders = squadCreatedDTO.getDefenders().stream()
                    .map(dto -> {
                        DefendersEntity entity = clubsSquadMapper.toDefendersEntity(dto);
                        entity.setClubsSquadInDefendersEntity(savedSquad);
                        defendersRepository.save(entity);
                        return entity;
                    }).collect(Collectors.toList());
            clubsSquad.setDefenderEntityList(defenders);
        }

        // 4. Midfielders
        if (squadCreatedDTO.getMidFielders() != null) {
            List<MidFieldersEntity> midfielders = squadCreatedDTO.getMidFielders().stream()
                    .map(dto -> {
                        MidFieldersEntity entity = clubsSquadMapper.toMidfieldersEntity(dto);
                        entity.setClubsSquadInMidfielder(savedSquad);
                        midfieldersRepository.save(entity);
                        return entity;
                    }).collect(Collectors.toList());
            clubsSquad.setMidFieldersEntityList(midfielders);
        }

        // 5. Strikers
        if (squadCreatedDTO.getStrikers() != null) {
            List<StrikersEntity> strikers = squadCreatedDTO.getStrikers().stream()
                    .map(dto -> {
                        StrikersEntity entity = clubsSquadMapper.toStrikersEntity(dto);
                        entity.setClubsSquadInStriker(savedSquad);
                        strikersRepository.save(entity);
                        return entity;
                    }).collect(Collectors.toList());
            clubsSquad.setStrikersEntityList(strikers);
        }

        // 6. Profile
        ClubsProfileEntity profile = clubsSquadMapper.getClubsProfileEntity(squadCreatedDTO);
        profile.setClubsSquadProfile(savedSquad);
        clubsSquad.setClubsProfileEntity(profile);
        clubsProfileRepository.save(profile);
        // 8. Convert to DTO
        return clubsSquadMapper.toClubsSquadDTO(savedSquad);
    }

    /// GET CLUBS ALL DATA IN LIST
    @Override
    public List<ClubsSquadDTO> getClubsInfoList() {
        List<ClubsSquadDTO> dtoList = clubsSquadRepository.findAll().stream().map(clubsSquadMapper::toClubsSquadDTO).collect(Collectors.toList());
        Collections.reverse(dtoList);
        return dtoList;
    }

    /// UPDATE CLUBS SQUAD
    @Override
    public ClubsSquadDTO updateSquad(String clubsId, String playerId, ClubsSquadCreatedDTO squadCreatedDTO) {
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
                    goalKeepersDTO -> {
                        GoalKeepersEntity goalKeepersEntity = clubsSquadMapper.toUpdateGoalKeeperEntity(goalKeepersDTO, playerId);
                        goalKeepersEntity.setClubsSquadInGoalKeepers(entity);//PARENT LINK
                        goalKeepersRepository.updateGoalkeepers(playerId, goalKeepersDTO.getFirstName(), goalKeepersDTO.getLastName(), goalKeepersDTO.getClubNumber());
                        return goalKeepersEntity;
                    }).collect(Collectors.toList());
        }
        entity.setGoalKeepersEntityList(goalKeepersEntityList);

        //update Defenders
        List<DefendersEntity> defendersEntityList = new ArrayList<>();
        if (squadCreatedDTO.getDefenders() != null) {
            defendersEntityList = squadCreatedDTO.getDefenders().stream().map(
                    defenders -> {
                        DefendersEntity defendersEntity = clubsSquadMapper.toUpdateDefendersEntity(defenders, playerId);
                        defendersEntity.setClubsSquadInDefendersEntity(entity);//PARENT LINK
                        defendersRepository.updateDefenders(playerId, defenders.getFirstName(), defenders.getLastName(), defenders.getClubNumber());
                        return defendersEntity;
                    }).collect(Collectors.toList());
        }
        entity.setDefenderEntityList(defendersEntityList);

        //update Midfielders
        List<MidFieldersEntity> midFieldersEntityList = new ArrayList<>();
        if (squadCreatedDTO.getMidFielders() != null) {
            midFieldersEntityList = squadCreatedDTO.getMidFielders().stream().map(
                    midFielders -> {
                        MidFieldersEntity midFieldersEntity = clubsSquadMapper.toUpdateMidfieldersEntity(midFielders, playerId);
                        midFieldersEntity.setClubsSquadInMidfielder(entity);//PARENT LINK
                        midfieldersRepository.updateMidFielders(playerId, midFielders.getFirstName(), midFielders.getLastName(), midFielders.getClubNumber());
                        return midFieldersEntity;
                    }).collect(Collectors.toList());
        }
        entity.setMidFieldersEntityList(midFieldersEntityList);

        //striker
        List<StrikersEntity> strikersEntityList = new ArrayList<>();
        if (squadCreatedDTO.getStrikers() != null) {
            strikersEntityList = squadCreatedDTO.getStrikers().stream().map(
                    strikers -> {
                        StrikersEntity strikersEntity = clubsSquadMapper.toUpdateStrikersEntity(strikers, playerId);
                        strikersEntity.setClubsSquadInStriker(entity);//PARENT LINK
                        strikersRepository.updateStrikers(playerId, strikers.getFirstName(), strikers.getLastName(), strikers.getClubNumber());
                        return strikersEntity;
                    }
            ).collect(Collectors.toList());
        }
        entity.setStrikersEntityList(strikersEntityList);

        //update clubs profile
        ClubsProfileEntity clubsProfile = clubsSquadMapper.toUpdateClubsProfileEntity(squadCreatedDTO, entity.getClubsSquadId());
        clubsProfile.setClubsSquadProfile(entity);
        clubsProfileRepository.updateClubsProfile(entity.getClubsSquadId(), clubsProfile.getStadiumName(),
                clubsProfile.getFounded(), clubsProfile.getClubsColor(), clubsProfile.getStreet(),
                clubsProfile.getCity(), clubsProfile.getDirection(), clubsProfile.getPhone(), clubsProfile.getFax(),
                clubsProfile.getWebsiteName(), clubsProfile.getEmailName());
        return clubsSquadMapper.toClubsSquadDTO(entity);
    }

    /// DELETE CLUBS SQUAD
    @Override
    public String deleteSquad(String clubsId) {
        clubsSquadRepository.deleteById(clubsId);
        return "";
    }
}
