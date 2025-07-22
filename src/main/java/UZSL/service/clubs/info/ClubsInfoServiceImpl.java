package UZSL.service.clubs.info;

import UZSL.config.util.SpringSecurityUtil;
import UZSL.dto.app.AppResponse;
import UZSL.dto.clubs.clubs_info.created.*;
import UZSL.dto.clubs.clubs_info.dto.*;
import UZSL.dto.extensions.ClubsSquadServiceDTO;
import UZSL.entity.clubs.clubsInfo.*;
import UZSL.entity.match.TeamsEntity;
import UZSL.enums.UzSlRoles;
import UZSL.exception.AppBadException;
import UZSL.repository.clubs.clubsInfo.*;
import UZSL.repository.match.TeamsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClubsInfoServiceImpl implements ClubsInfoService {

    @Autowired
    private ClubsSquadRepository clubsSquadRepository;
    @Autowired
    private TeamsRepository teamsRepository;
    @Autowired
    private ClubsSquadServiceDTO clubsSquadServiceDTO;


    @Override
    public ClubsSquadDTO createSquadAndProfile(Integer userId, String clubsId, ClubsSquadCreatedDTO squadCreatedDTO) {
        Integer currentUser = SpringSecurityUtil.getCurrentUserId();
        if (!userId.equals(currentUser) && !SpringSecurityUtil.hasRole(UzSlRoles.ROLE_ADMIN)) {
            throw new AppBadException("User is not found!");
        }
        Optional<TeamsEntity> optionalTeams = teamsRepository.findById(clubsId);

        ClubsSquadEntity clubsSquad = new ClubsSquadEntity();
        clubsSquad.setClubsFullName(optionalTeams.get().getHomeTeamEntity().getHomeTeamName());
        clubsSquad.setTeamsClubsSquad(optionalTeams.get()); // PARENT LINK
        ClubsSquadEntity saved = clubsSquadRepository.save(clubsSquad);

        //Goal keepers
        List<GoalKeepersEntity> goalKeepersEntityList = new ArrayList<>();
        if (squadCreatedDTO.getGoalKeepers() != null) {
            goalKeepersEntityList = squadCreatedDTO.getGoalKeepers().stream().map(
                    goalKeepersDTO -> clubsSquadServiceDTO.toGoalKeeperEntity(goalKeepersDTO, saved)).collect(Collectors.toList());
        }
        clubsSquad.setGoalKeepersEntityList(goalKeepersEntityList);

        //Defenders
        List<DefendersEntity> defendersEntityList = new ArrayList<>();
        if (squadCreatedDTO.getDefenders() != null) {
            defendersEntityList = squadCreatedDTO.getDefenders().stream().map(
                    defenders -> clubsSquadServiceDTO.toDefendersEntity(defenders, saved)).collect(Collectors.toList());
        }
        clubsSquad.setDefenderEntityList(defendersEntityList);

        //Midfielders
        List<MidFieldersEntity> midFieldersEntityList = new ArrayList<>();
        if (squadCreatedDTO.getDefenders() != null) {
            midFieldersEntityList = squadCreatedDTO.getMidFielders().stream().map(
                    midFielders -> clubsSquadServiceDTO.toMidfieldersEntity(midFielders, saved)).collect(Collectors.toList());
        }
        clubsSquad.setMidFieldersEntityList(midFieldersEntityList);

        //Strikers
        List<StrikersEntity> strikersEntityList = new ArrayList<>();
        if (squadCreatedDTO.getDefenders() != null) {
            strikersEntityList = squadCreatedDTO.getStrikers().stream().map(
                    strikers -> clubsSquadServiceDTO.toStrikersEntity(strikers, saved)).collect(Collectors.toList());
        }
        clubsSquad.setStrikersEntityList(strikersEntityList);

        //Clubs Profile details
        clubsSquadServiceDTO.getClubsProfileEntity(squadCreatedDTO, saved);
        return clubsSquadServiceDTO.toClubsSquadDTO(clubsSquad);
    }

    @Override
    public List<ClubsSquadDTO> getClubsInfoList() {
        List<ClubsSquadEntity> squadEntityList = clubsSquadRepository.findAll();
        return squadEntityList.stream().map(clubsSquadServiceDTO::toClubsSquadDTO).collect(Collectors.toList());
    }

    @Override
    public ClubsSquadDTO updateSquad(String clubsId, ClubsSquadCreatedDTO squadCreatedDTO) {
        Optional<ClubsSquadEntity> optionalClubsSquad = clubsSquadRepository.findById(clubsId);
        if (optionalClubsSquad.isEmpty()) {
            throw new AppBadException("Squad id: " + clubsId + " is not found!");
        }
        //TeamsEntity id is taking and checking for creation of clubs info
        Optional<TeamsEntity> optionalTeams = teamsRepository.findById(clubsId);

        //Parent entity
        ClubsSquadEntity entity = optionalClubsSquad.get();
        entity.setClubsFullName(squadCreatedDTO.getClubsFullName());
        entity.setTeamsClubsSquad(optionalTeams.get()); // PARENT LINK
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
        if (squadCreatedDTO.getDefenders() != null) {
            midFieldersEntityList = squadCreatedDTO.getMidFielders().stream().map(
                    midFielders -> clubsSquadServiceDTO.toUpdateMidfieldersEntity(midFielders, saved)).collect(Collectors.toList());
        }
        entity.setMidFieldersEntityList(midFieldersEntityList);

        //update Strikers
        List<StrikersEntity> strikersEntityList = new ArrayList<>();
        if (squadCreatedDTO.getDefenders() != null) {
            strikersEntityList = squadCreatedDTO.getStrikers().stream().map(
                    strikers -> clubsSquadServiceDTO.toUpdateStrikersEntity(strikers, saved)).collect(Collectors.toList());
        }
        entity.setStrikersEntityList(strikersEntityList);
        //update clubs profile
        clubsSquadServiceDTO.updateClubsProfileEntity(squadCreatedDTO, saved);
        return clubsSquadServiceDTO.toClubsSquadDTO(entity);
    }

    @Override
    public String deleteSquad(String clubsId) {
        clubsSquadRepository.deleteById(clubsId);
        return "";
    }
}
