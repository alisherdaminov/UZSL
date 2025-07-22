package UZSL.service.match;

import UZSL.config.util.SpringSecurityUtil;
import UZSL.dto.extensions.MatchServiceDTO;
import UZSL.dto.match.dto.MatchDTO;
import UZSL.dto.match.dto.TeamsDTO;
import UZSL.dto.match.created.MatchCreatedDTO;
import UZSL.dto.match.teams_logo.updated_logo_created.MatchUpdateLogoCreatedDTO;
import UZSL.dto.match.teams_logo.updated_logo_dto.MatchUpdatedLogoDTO;
import UZSL.dto.match.updateDTO.dto.MatchUpdatedDTO;
import UZSL.dto.match.updateDTO.created.MatchUpdateCreatedDTO;
import UZSL.entity.match.AwayTeamEntity;
import UZSL.entity.match.HomeTeamEntity;
import UZSL.entity.match.MatchEntity;
import UZSL.entity.match.TeamsEntity;
import UZSL.enums.UzSlRoles;
import UZSL.exception.AppBadException;
import UZSL.repository.match.AwayTeamRepository;
import UZSL.repository.match.HomeTeamRepository;
import UZSL.repository.match.MatchRepository;
import UZSL.repository.match.TeamsRepository;
import UZSL.service.clubs.info.ClubsInfoServiceImpl;
import UZSL.service.match.logo.MatchLogoService;
import UZSL.service.clubs.table.ClubsTableServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MatchServiceImpl implements MatchService {

    @Autowired
    private MatchRepository matchRepository;
    @Autowired
    private HomeTeamRepository homeTeamRepository;
    @Autowired
    private AwayTeamRepository awayTeamRepository;
    @Autowired
    private TeamsRepository teamsRepository;
    @Autowired
    private MatchLogoService matchLogoService;
    @Autowired
    private ClubsTableServiceImpl clubsTableService;
    @Autowired
    private ClubsInfoServiceImpl clubsInfoService;
    @Autowired
    private MatchServiceDTO matchServiceDTO;


    ///  CREATE MATCH DAYS WITH CLUBS
    @Override
    public MatchDTO createMatch(Integer userId, MatchCreatedDTO matchCreatedDTO) {
        Integer currentUser = SpringSecurityUtil.getCurrentUserId();
        if (!SpringSecurityUtil.hasRole(UzSlRoles.ROLE_ADMIN) && !userId.equals(currentUser)) {
            throw new AppBadException("You are not allowed to make the matches!");
        }
        // creation MatchEntity
        MatchEntity entity = new MatchEntity();
        entity.setMatchStartedDate(matchCreatedDTO.getMatchStartedDate());
        entity.setMatchStartedTime(matchCreatedDTO.getMatchStartedTime());
        entity.setUserId(currentUser);
        entity.setProcessed(true);
        MatchEntity savedMatch = matchRepository.save(entity);

        // TeamsEntity into MatchEntity
        List<TeamsEntity> teamsEntityList = new ArrayList<>();
        if (matchCreatedDTO.getTeamsCreatedList() != null) {
            teamsEntityList = matchCreatedDTO.getTeamsCreatedList().stream()
                    .map(dto -> matchServiceDTO.toTeamsEntity(dto, savedMatch)).collect(Collectors.toList());
        }
        entity.setTeamsEntityList(teamsEntityList);
        //  return to  DTO
        return matchServiceDTO.toMatchesDTO(entity);
    }

    /// GET MATCHES DAYS AND CLUB BY ID
    @Override
    public MatchDTO getByIdMatchesData(String matchId) {
        MatchEntity entity = matchRepository.findById(matchId).orElseThrow(() -> new AppBadException("Match id: " + matchId + " is not found!"));
        return matchServiceDTO.toMatchesDTO(entity);
    }

    /// GET TEAMS BY ID
    @Override
    public TeamsDTO getByIdTeamsData(String teamsId) {
        TeamsEntity entity = teamsRepository.findById(teamsId).orElseThrow(() -> new AppBadException("Teams id: " + teamsId + " is not found!"));
        return matchServiceDTO.toTeamsDTO(entity);
    }

    ///  GET ALL MATCH DAYS AND CLUBS LIST
    @Override
    public List<MatchDTO> getAllMatchesData() {
        List<MatchEntity> entityList = matchRepository.findAll();
        return entityList.stream().map(matchServiceDTO::toMatchesDTO).collect(Collectors.toList());
    }

    ///  UPDATE CLUBS LOGO BY ID
    @Override
    public MatchUpdatedLogoDTO updateClubLogo(String matchId, String homeTeamsId, String awayTeamsId, MatchUpdateLogoCreatedDTO createdDTO) {
        MatchEntity entity = matchRepository.findById(matchId).orElseThrow(() -> new AppBadException("Match not found!"));
        List<TeamsEntity> updatedList = createdDTO.getTeamsUpdateLogoCreatedDTOList().stream().map(
                info -> {
                    TeamsEntity club = teamsRepository.findByHomeTeamEntity_HomeTeamIdAndAwayTeamEntity_AwayTeamId(homeTeamsId, awayTeamsId).orElseThrow(() -> new AppBadException("Club not found"));
                    HomeTeamEntity homeTeam = homeTeamRepository.findById(homeTeamsId).orElseThrow(() -> new AppBadException("Home team id: " + homeTeamsId + " is not found!"));
                    AwayTeamEntity awayTeam = awayTeamRepository.findById(awayTeamsId).orElseThrow(() -> new AppBadException("Away team id: " + awayTeamsId + " is not found!"));

                    //Home team logo
                    String homeTeamLogoOld = null;
                    if (!info.getHomeTeamUpdateLogoCreatedDTO().getHomeLogoDTO().getTeamsLogoCreatedId().equals(homeTeam.getHomeTeamLogoId())) {
                        homeTeamLogoOld = homeTeam.getHomeTeamLogoId();
                    }
                    homeTeam.setHomeTeamLogoId(info.getHomeTeamUpdateLogoCreatedDTO().getHomeLogoDTO().getTeamsLogoCreatedId());
                    if (homeTeamLogoOld != null) {
                        matchLogoService.updateHomeTeamLogo(homeTeamLogoOld);
                    }

                    //Away team logo
                    String awayTeamLogoOld = null;
                    if (!info.getAwayTeamUpdateLogoCreatedDTO().getAwayTeamsLogoDTO().getTeamsLogoCreatedId().equals(awayTeam.getAwayTeamId())) {
                        awayTeamLogoOld = awayTeam.getAwayTeamId();
                    }
                    awayTeam.setAwayTeamLogoId(info.getAwayTeamUpdateLogoCreatedDTO().getAwayTeamsLogoDTO().getTeamsLogoCreatedId());
                    if (awayTeamLogoOld != null) {
                        matchLogoService.updateAwayTeamLogo(awayTeamLogoOld);
                    }

                    club.setTeamsMatchEntity(entity); // Parent link
                    club.setHomeTeamEntity(homeTeam);
                    club.setAwayTeamEntity(awayTeam);
                    teamsRepository.save(club);
                    return club;
                }).collect(Collectors.toList());
        entity.setTeamsEntityList(updatedList);
        matchRepository.save(entity);
        return matchServiceDTO.toUpdateClubLogoDTO(entity);
    }

    ///  UPDATE CLUBS GOALS AFTER EVERY MATCH ENDS
    @Override
    public MatchUpdatedDTO updatedMatch(String matchId, String homeTeamsId, String awayTeamsId, MatchUpdateCreatedDTO createdDTO) {
        MatchEntity entity = matchRepository.findById(matchId).orElseThrow(() -> new AppBadException("Match not found!"));
        List<TeamsEntity> updatedList = createdDTO.getTeamsUpdateCreatedDTOList().stream().map(
                info -> {
                    // Parent team
                    TeamsEntity club = teamsRepository.findByHomeTeamEntity_HomeTeamIdAndAwayTeamEntity_AwayTeamId(homeTeamsId, awayTeamsId).orElseThrow(() -> new AppBadException("Club not found"));
                    HomeTeamEntity homeTeam = homeTeamRepository.findById(homeTeamsId).orElseThrow(() -> new AppBadException("Home team id: " + homeTeamsId + " is not found!"));
                    AwayTeamEntity awayTeam = awayTeamRepository.findById(awayTeamsId).orElseThrow(() -> new AppBadException("Away team id: " + awayTeamsId + " is not found!"));
                    /////////////////////////////////////////////////////
                    List<TeamsEntity> teamsEntityList = teamsRepository.findAllWithTeams();
                    for (TeamsEntity teamsEntity : teamsEntityList) {
                        //Home team goals will set and once teams logo update
                        if (homeTeamsId.equals(teamsEntity.getHomeTeamEntity().getHomeTeamId())) {
                            homeTeam.setOwnGoal(info.getHomeTeamUpdateCreatedDTO().getOwnGoal());
                            homeTeam.setTeamsEntity(club); // Parent link
                            homeTeamRepository.save(homeTeam);
                        }
                        /////////////////////////////////////////////////////
                        //Away team goals will set and once teams logo update
                        if (awayTeamsId.equals(teamsEntity.getAwayTeamEntity().getAwayTeamId())) {
                            awayTeam.setAwayGoal(info.getAwayTeamUpdateCreatedDTO().getAwayGoal());
                            awayTeam.setTeamsEntity(club); // Parent link
                            awayTeamRepository.save(awayTeam);
                        }
                        /////////////////////////////////////////////////////
                    }
                    // Parent team
                    club.setTeamsMatchEntity(entity); // Parent link
                    club.setHomeTeamEntity(homeTeam);
                    club.setAwayTeamEntity(awayTeam);
                    teamsRepository.save(club);
                    return club;
                }).collect(Collectors.toList());
        entity.setTeamsEntityList(updatedList);
        if (entity.isProcessed()) {
            ///  this method is for calculation of UZSL table in real time, once update function is done!
            clubsTableService.calculateTeamStatsFromMatches(homeTeamsId, awayTeamsId);
        }
        matchRepository.save(entity);
        return matchServiceDTO.toUpdateDTO(entity);
    }

    /// DELETE MATCHES DAYS AND CLUBS BY ID IN DATABASE
    @Override
    public String deleteMatchById(String matchId) {
        matchRepository.deleteById(matchId);
        return "Deleted match: " + matchId;
    }

    /// DELETE TEAMS BY ID IN DATABASE
    @Override
    public String deleteTeamsById(String teamsId) {
        teamsRepository.deleteById(teamsId);
        return "Deleted teams: " + teamsId;
    }

}
