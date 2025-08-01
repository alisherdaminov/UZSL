package UZSL.application.service.match;

import UZSL.domain.service.match.MatchService;
import UZSL.shared.util.SpringSecurityUtil;
import UZSL.application.mapper.MatchMapper;
import UZSL.application.dto.match.dto.MatchDTO;
import UZSL.application.dto.match.dto.TeamsDTO;
import UZSL.application.dto.match.created.MatchCreatedDTO;
import UZSL.application.dto.match.teams_logo.updated_logo_created.MatchUpdateLogoCreatedDTO;
import UZSL.application.dto.match.teams_logo.updated_logo_dto.MatchUpdatedLogoDTO;
import UZSL.application.dto.match.updateDTO.dto.MatchUpdatedDTO;
import UZSL.application.dto.match.updateDTO.created.MatchUpdateCreatedDTO;
import UZSL.domain.model.entity.match.AwayTeamEntity;
import UZSL.domain.model.entity.match.HomeTeamEntity;
import UZSL.domain.model.entity.match.MatchEntity;
import UZSL.domain.model.entity.match.TeamsEntity;
import UZSL.shared.enums.UzSlRoles;
import UZSL.shared.exception.AppBadException;
import UZSL.domain.repository.match.AwayTeamRepository;
import UZSL.domain.repository.match.HomeTeamRepository;
import UZSL.domain.repository.match.MatchRepository;
import UZSL.domain.repository.match.TeamsRepository;
import UZSL.application.service.clubs.info.ClubsInfoServiceImpl;
import UZSL.application.service.match.logo.MatchLogoService;
import UZSL.application.service.clubs.table.ClubsTableServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * MatchServiceImpl implements MatchService override bellow functions these are for creation of UZSL clubs, matches\
 * clubs logo is also creating and set in DB, and can be updated
 * before saving matches that teams divided into home and away parts which lead to create new seasons matches
 * there is also a ClubsTableServiceImpl which the method is for calculation of UZSL table in real time, once update function is done!
 * */
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
    private MatchMapper matchMapper;


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
                    .map(dto -> matchMapper.toTeamsEntity(dto, savedMatch)).collect(Collectors.toList());
        }
        entity.setTeamsEntityList(teamsEntityList);
        //  return to  DTO
        return matchMapper.toMatchesDTO(entity);
    }

    /// GET MATCHES DAYS AND CLUB BY ID
    @Override
    public MatchDTO getByIdMatchesData(String matchId) {
        MatchEntity entity = matchRepository.findById(matchId).orElseThrow(() -> new AppBadException("Match id: " + matchId + " is not found!"));
        return matchMapper.toMatchesDTO(entity);
    }

    /// GET TEAMS BY ID
    @Override
    public TeamsDTO getByIdTeamsData(String teamsId) {
        TeamsEntity entity = teamsRepository.findById(teamsId).orElseThrow(() -> new AppBadException("Teams id: " + teamsId + " is not found!"));
        return matchMapper.toTeamsDTO(entity);
    }

    ///  GET ALL MATCH DAYS AND CLUBS LIST
    @Override
    public List<MatchDTO> getAllMatchesData() {
        List<MatchDTO> entityList = matchRepository.findAll().stream().map(matchMapper::toMatchesDTO).collect(Collectors.toList());
        Collections.reverse(entityList);
        return entityList;
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
        return matchMapper.toUpdateClubLogoDTO(entity);
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
        return matchMapper.toUpdateDTO(entity);
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
