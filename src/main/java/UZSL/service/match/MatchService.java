package UZSL.service.match;

import UZSL.config.util.SpringSecurityUtil;
import UZSL.dto.match.AwayTeamDTO;
import UZSL.dto.match.HomeTeamDTO;
import UZSL.dto.match.MatchDTO;
import UZSL.dto.match.TeamsDTO;
import UZSL.dto.match.created.MatchCreatedDTO;
import UZSL.dto.match.created.TeamsCreatedDTO;
import UZSL.dto.match.updateDTO.AwayTeamUpdatedDTO;
import UZSL.dto.match.updateDTO.HomeTeamUpdatedDTO;
import UZSL.dto.match.updateDTO.MatchUpdatedDTO;
import UZSL.dto.match.updateDTO.TeamsUpdatedDTO;
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
import UZSL.service.table.ClubsTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MatchService {

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
    private ClubsTableService clubsTableService;

    ///  CREATE
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
        entity.setChampionsLeague(matchCreatedDTO.getChampionsLeague());
        entity.setAfcCup(matchCreatedDTO.getAfcCup());
        entity.setConferenceLeague(matchCreatedDTO.getConferenceLeague());
        entity.setPlayOff(matchCreatedDTO.getPlayOff());
        entity.setRelegation(matchCreatedDTO.getRelegation());
        entity.setProcessed(true);
        MatchEntity savedMatch = matchRepository.save(entity);

        // TeamsEntity into MatchEntity
        List<TeamsEntity> teamsEntityList = new ArrayList<>();
        if (matchCreatedDTO.getTeamsCreatedList() != null) {
            teamsEntityList = matchCreatedDTO.getTeamsCreatedList().stream()
                    .map(dto -> toTeamsEntity(dto, savedMatch)).collect(Collectors.toList());
        }
        entity.setTeamsEntityList(teamsEntityList);
        //  return to  DTO
        return toMatchesDTO(entity);
    }

    /// GET BY ID MATCHES
    public MatchDTO getByIdMatchesData(String matchId) {
        MatchEntity entity = matchRepository.findById(matchId).orElseThrow(() -> new AppBadException("Match id: " + matchId + " is not found!"));
        return toMatchesDTO(entity);
    }

    /// GET BY ID TEAMS
    public TeamsDTO getByIdTeamsData(String teamsId) {
        TeamsEntity entity = teamsRepository.findById(teamsId).orElseThrow(() -> new AppBadException("Teams id: " + teamsId + " is not found!"));
        return toTeamsDTO(entity);
    }

    ///  GET ALL
    public List<MatchDTO> getAllMatchesData() {
        List<MatchEntity> entityList = matchRepository.findAll();
        return entityList.stream().map(this::toMatchesDTO).collect(Collectors.toList());
    }

    /// UPDATE
    public MatchUpdatedDTO updatedMatch(String matchId, String homeTeamsId, String awayTeamsId, MatchUpdateCreatedDTO createdDTO) {
        MatchEntity entity = matchRepository.findById(matchId).orElseThrow(() -> new AppBadException("Match not found!"));
        List<TeamsEntity> updatedList = createdDTO.getTeamsUpdateCreatedDTOList().stream().map(
                info -> {
                    // Parent team
                    TeamsEntity club = new TeamsEntity();
                    HomeTeamEntity homeTeam = new HomeTeamEntity();
                    AwayTeamEntity awayTeam = new AwayTeamEntity();
                    /////////////////////////////////////////////////////
                    List<TeamsEntity> teamsEntityList = teamsRepository.findAll();
                    for (TeamsEntity teamsEntity : teamsEntityList) {
                        //Home team goals will set and once teams logo update
                        if (homeTeamsId.equals(teamsEntity.getHomeTeamEntity().getHomeTeamId())) {
                            homeTeam.setOwnGoal(info.getHomeTeamUpdateCreatedDTO().getOwnGoal());
                            homeTeam.setTeamsEntity(club); // Parent link
                            // LOGO of home team
                            String homeTeamLogoOld = null;
                            if (!info.getHomeTeamUpdateCreatedDTO().getHomeLogoDTO().getTeamsLogoCreatedId().equals(homeTeam.getHomeTeamLogoId())) {
                                homeTeamLogoOld = homeTeam.getHomeTeamLogoId();
                            }
                            homeTeam.setHomeTeamLogoId(info.getHomeTeamUpdateCreatedDTO().getHomeLogoDTO().getTeamsLogoCreatedId());
                            if (homeTeamLogoOld != null) {
                                matchLogoService.updateHomeTeamLogo(homeTeamLogoOld);
                            }
                        }
                        /////////////////////////////////////////////////////
                        //Away team goals will set and once teams logo update
                        if (awayTeamsId.equals(teamsEntity.getAwayTeamEntity().getAwayTeamId())) {
                            awayTeam.setAwayGoal(info.getAwayTeamUpdateCreatedDTO().getAwayGoal());
                            awayTeam.setTeamsEntity(club); // Parent link
                            // LOGO of away team
                            String awayTeamLogoOld = null;
                            if (!info.getAwayTeamUpdateCreatedDTO().getAwayTeamsLogoDTO().getTeamsLogoCreatedId().equals(awayTeam.getAwayTeamId())) {
                                awayTeamLogoOld = awayTeam.getAwayTeamId();
                            }
                            awayTeam.setAwayTeamLogoId(info.getAwayTeamUpdateCreatedDTO().getAwayTeamsLogoDTO().getTeamsLogoCreatedId());
                            if (awayTeamLogoOld != null) {
                                matchLogoService.updateAwayTeamLogo(awayTeamLogoOld);
                            }
                        }
                        /// All season's goals will be setting and updating here
                        teamsRepository.updateTeamGoals(teamsEntity.getTeamsId(), homeTeam.getOwnGoal(), awayTeam.getAwayGoal());
                        /////////////////////////////////////////////////////
                        if (entity.isProcessed() && entity.getMatchId() != null
                                && homeTeamsId.equals(teamsEntity.getHomeTeamEntity().getHomeTeamId())
                                && awayTeamsId.equals(teamsEntity.getAwayTeamEntity().getAwayTeamId())) {
                            ///  this method is for calculation of UZSL table in real time
                            clubsTableService.calculateTeamStatsFromMatches();
                        }
                    }
                    // Parent team
                    club.setTeamsMatchEntity(entity); // Parent link
                    club.setHomeTeamEntity(homeTeam);
                    club.setAwayTeamEntity(awayTeam);
                    return club;
                }).collect(Collectors.toList());
        entity.setTeamsEntityList(updatedList);
        matchRepository.save(entity);
        return toUpdateDTO(entity);
    }

    /// DELETE MATCHES
    public String deleteMatchById(String matchId) {
        matchRepository.deleteById(matchId);
        return "Deleted match: " + matchId;
    }

    /// DELETE TEAMS
    public String deleteTeamsById(String teamsId) {
        teamsRepository.deleteById(teamsId);
        return "Deleted teams: " + teamsId;
    }

    // TO HOME TEAM ENTITY
    public TeamsEntity toTeamsEntity(TeamsCreatedDTO createdDTO, MatchEntity matchEntity) {

        List<TeamsEntity> optionalTeams = teamsRepository.findAll();
        for (TeamsEntity teamsEntity : optionalTeams) {
            String home = teamsEntity.getHomeTeamEntity().getHomeTeamName().toLowerCase().toUpperCase();
            String away = teamsEntity.getAwayTeamEntity().getAwayTeamName().toLowerCase().toUpperCase();
            if (home.equals(createdDTO.getHomeTeam().getHomeTeamName()) && away.equals(createdDTO.getAwayTeam().getAwayTeamName())) {
                throw new AppBadException("This club: " + home + " or " + away + " already exists!");
            }
        }

        // new homeTeam
        HomeTeamEntity homeTeam = new HomeTeamEntity();
        homeTeam.setHomeTeamName(createdDTO.getHomeTeam().getHomeTeamName());
        homeTeam.setHomeTeamLogoId(createdDTO.getHomeTeam().getHomeLogoDTO().getTeamsLogoCreatedId());
        HomeTeamEntity savedHomeTeam = homeTeamRepository.save(homeTeam);

        // new awayTeam
        AwayTeamEntity awayTeam = new AwayTeamEntity();
        awayTeam.setAwayTeamName(createdDTO.getAwayTeam().getAwayTeamName());
        awayTeam.setAwayTeamLogoId(createdDTO.getAwayTeam().getAwayTeamsLogoDTO().getTeamsLogoCreatedId());
        AwayTeamEntity savedAwayTeam = awayTeamRepository.save(awayTeam);

        // new TeamsEntity
        TeamsEntity newTeams = new TeamsEntity();
        newTeams.setHomeTeamEntity(savedHomeTeam);
        newTeams.setAwayTeamEntity(savedAwayTeam);
        newTeams.setTeamsMatchEntity(matchEntity);
        return teamsRepository.save(newTeams);
    }

    public MatchUpdatedDTO toUpdateDTO(MatchEntity entity) {
        MatchUpdatedDTO dto = new MatchUpdatedDTO();
        dto.setMatchUpdatedId(entity.getMatchId());
        List<TeamsUpdatedDTO> teamsDTOList = entity.getTeamsEntityList().stream().map(
                infoDto -> {
                    // home dto set
                    HomeTeamUpdatedDTO homeTeamDTO = new HomeTeamUpdatedDTO();
                    homeTeamDTO.setHomeTeamId(infoDto.getHomeTeamEntity().getHomeTeamId());
                    homeTeamDTO.setOwnGoal(infoDto.getHomeTeamEntity().getOwnGoal());
                    homeTeamDTO.setHomeTeamsLogo(matchLogoService.teamsLogoDTO(infoDto.getHomeTeamEntity().getHomeTeamLogoId()));
                    /////////////////////////////////////////////////////
                    // away dto set
                    AwayTeamUpdatedDTO awayTeamDTO = new AwayTeamUpdatedDTO();
                    awayTeamDTO.setAwayTeamId(infoDto.getAwayTeamEntity().getAwayTeamId());
                    awayTeamDTO.setAwayGoal(infoDto.getAwayTeamEntity().getAwayGoal());
                    awayTeamDTO.setAwayTeamsLogo(matchLogoService.teamsLogoDTO(infoDto.getAwayTeamEntity().getAwayTeamLogoId()));
                    /////////////////////////////////////////////////////
                    // set teams dto
                    TeamsUpdatedDTO teamsDTO = new TeamsUpdatedDTO();
                    teamsDTO.setTeamsId(infoDto.getTeamsId());
                    teamsDTO.setHomeTeam(homeTeamDTO);
                    teamsDTO.setAwayTeam(awayTeamDTO);
                    return teamsDTO;
                }
        ).collect(Collectors.toList());
        dto.setTeamsUpdatedList(teamsDTOList);
        return dto;
    }

    // TO DTO
    public MatchDTO toMatchesDTO(MatchEntity entity) {
        MatchDTO dto = new MatchDTO();
        dto.setMatchId(entity.getMatchId());
        dto.setMatchStartedDate(entity.getMatchStartedDate());
        dto.setMatchStartedTime(entity.getMatchStartedTime());
        dto.setChampionsLeague(entity.getChampionsLeague());
        dto.setAfcCup(entity.getAfcCup());
        dto.setConferenceLeague(entity.getConferenceLeague());
        dto.setPlayOff(entity.getPlayOff());
        dto.setRelegation(entity.getRelegation());
        // teams entity in dto list shown
        List<TeamsDTO> teamsDTOList = entity.getTeamsEntityList().stream().map(
                infoDto -> {
                    // home dto set
                    HomeTeamDTO homeTeamDTO = new HomeTeamDTO();
                    homeTeamDTO.setHomeTeamId(infoDto.getHomeTeamEntity().getHomeTeamId());
                    homeTeamDTO.setHomeTeamName(infoDto.getHomeTeamEntity().getHomeTeamName());
                    homeTeamDTO.setHomeTeamsLogo(matchLogoService.teamsLogoDTO(infoDto.getHomeTeamEntity().getHomeTeamLogoId()));
                    /////////////////////////////////////////////////////
                    // away dto set
                    AwayTeamDTO awayTeamDTO = new AwayTeamDTO();
                    awayTeamDTO.setAwayTeamId(infoDto.getAwayTeamEntity().getAwayTeamId());
                    awayTeamDTO.setAwayTeamName(infoDto.getAwayTeamEntity().getAwayTeamName());
                    awayTeamDTO.setAwayTeamsLogo(matchLogoService.teamsLogoDTO(infoDto.getAwayTeamEntity().getAwayTeamLogoId()));
                    /////////////////////////////////////////////////////
                    // set teams dto
                    TeamsDTO teamsDTO = new TeamsDTO();
                    teamsDTO.setTeamsId(infoDto.getTeamsId());
                    teamsDTO.setHomeTeam(homeTeamDTO);
                    teamsDTO.setAwayTeam(awayTeamDTO);
                    return teamsDTO;
                }
        ).collect(Collectors.toList());
        dto.setTeamsList(teamsDTOList);
        return dto;
    }

    public TeamsDTO toTeamsDTO(TeamsEntity entity) {
        // home dto set
        HomeTeamDTO homeTeamDTO = new HomeTeamDTO();
        homeTeamDTO.setHomeTeamId(entity.getHomeTeamEntity().getHomeTeamId());
        homeTeamDTO.setHomeTeamName(entity.getHomeTeamEntity().getHomeTeamName());
        homeTeamDTO.setHomeTeamsLogo(matchLogoService.teamsLogoDTO(homeTeamDTO.getHomeTeamsLogo().getTeamsLogoId()));
        /////////////////////////////////////////////////////
        // away dto set
        AwayTeamDTO awayTeamDTO = new AwayTeamDTO();
        awayTeamDTO.setAwayTeamId(entity.getAwayTeamEntity().getAwayTeamId());
        awayTeamDTO.setAwayTeamName(entity.getAwayTeamEntity().getAwayTeamName());
        awayTeamDTO.setAwayTeamsLogo(matchLogoService.teamsLogoDTO(awayTeamDTO.getAwayTeamsLogo().getTeamsLogoId()));
        /////////////////////////////////////////////////////
        // set teams dto
        TeamsDTO teamsDTO = new TeamsDTO();
        teamsDTO.setTeamsId(entity.getTeamsId());
        teamsDTO.setHomeTeam(homeTeamDTO);
        teamsDTO.setAwayTeam(awayTeamDTO);
        return teamsDTO;
    }

}
