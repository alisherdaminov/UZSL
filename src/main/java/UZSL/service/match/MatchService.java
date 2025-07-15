package UZSL.service.match;

import UZSL.config.util.SpringSecurityUtil;
import UZSL.dto.match.AwayTeamDTO;
import UZSL.dto.match.HomeTeamDTO;
import UZSL.dto.match.MatchDTO;
import UZSL.dto.match.TeamsDTO;
import UZSL.dto.match.created.MatchCreatedDTO;
import UZSL.dto.match.created.TeamsCreatedDTO;
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

import java.util.ArrayList;
import java.util.List;
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
        List<TeamsEntity> teamsRepositoryAll = teamsRepository.findAll();
        for (TeamsEntity teams : teamsRepositoryAll) {
            int clubsNumber = matchRepository.countMatchesByClubsMatchInfoId(teams.getTeamsId());
            if (clubsNumber <= 10) {
                if (SpringSecurityUtil.hasRole(UzSlRoles.ROLE_ADMIN) && userId.equals(currentUser)) {
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
                    MatchEntity savedMatches = matchRepository.save(entity);
                    // teams data is in list
                    List<TeamsEntity> teamsEntityList = new ArrayList<>();
                    if (matchCreatedDTO.getTeamsCreatedList() != null) {
                        teamsEntityList = matchCreatedDTO.getTeamsCreatedList().stream().map(
                                teamsInfo -> toTeamsEntity(teamsInfo, savedMatches)).collect(Collectors.toList());
                    }
                    entity.setTeamsEntityList(teamsEntityList);
                    return toMatchesDTO(entity);
                }
            } else {
                throw new AppBadException("Club\'s number must be 20!");
            }
        }
        throw new AppBadException("Unauthorized attempt to create post");
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
    public MatchDTO updatedMatch(String matchId, MatchCreatedDTO createdDTO) {
        MatchEntity entity = matchRepository.findById(matchId).orElseThrow(() -> new AppBadException("Match not found!"));
        entity.setMatchStartedDate(createdDTO.getMatchStartedDate());
        entity.setMatchStartedTime(createdDTO.getMatchStartedTime());
        entity.setChampionsLeague(createdDTO.getChampionsLeague());
        entity.setAfcCup(createdDTO.getAfcCup());
        entity.setConferenceLeague(createdDTO.getConferenceLeague());
        entity.setPlayOff(createdDTO.getPlayOff());
        entity.setRelegation(createdDTO.getRelegation());

        List<TeamsEntity> updatedList = createdDTO.getTeamsCreatedList().stream().map(
                info -> {
                    // Parent team
                    TeamsEntity club = new TeamsEntity();
                    /////////////////////////////////////////////////////
                    //Home team
                    HomeTeamEntity homeTeam = new HomeTeamEntity();
                    homeTeam.setOwnGoal(info.getHomeTeam().getOwnGoal());
                    homeTeam.setHomeTeamsEntity(club); // Parent link
                    String homeTeamLogoOld = null;
                    if (!info.getHomeTeam().getHomeLogoDTO().getTeamsLogoCreatedId().equals(homeTeam.getHomeTeamLogoId())) {
                        homeTeamLogoOld = homeTeam.getHomeTeamLogoId();
                    }
                    homeTeam.setHomeTeamLogoId(info.getHomeTeam().getHomeLogoDTO().getTeamsLogoCreatedId());
                    if (homeTeamLogoOld != null) {
                        matchLogoService.updateHomeTeamLogo(homeTeamLogoOld);
                    }
                    /////////////////////////////////////////////////////
                    //Away team
                    AwayTeamEntity awayTeam = new AwayTeamEntity();
                    awayTeam.setAwayGoal(info.getAwayTeam().getAwayGoal());
                    awayTeam.setAwayTeamsEntity(club); // Parent link
                    String awayTeamLogoOld = null;
                    if (!info.getAwayTeam().getAwayTeamsLogoDTO().getTeamsLogoCreatedId().equals(awayTeam.getAwayTeamId())) {
                        awayTeamLogoOld = awayTeam.getAwayTeamId();
                    }
                    awayTeam.setAwayTeamLogoId(info.getAwayTeam().getAwayTeamsLogoDTO().getTeamsLogoCreatedId());
                    if (awayTeamLogoOld != null) {
                        matchLogoService.updateAwayTeamLogo(awayTeamLogoOld);
                    }
                    /////////////////////////////////////////////////////
                    // Parent team
                    club.setHomeTeamEntity(homeTeam);
                    club.setAwayTeamEntity(awayTeam);
                    club.setTeamsMatchEntity(entity); // Parent link
                    return club;
                }).collect(Collectors.toList());
        entity.setTeamsEntityList(updatedList);
        matchRepository.save(entity);

        if (entity.isProcessed()) {
            clubsTableService.calculateTeamStatsFromMatches();
        }
        return toMatchesDTO(entity);
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
    public TeamsEntity toTeamsEntity(TeamsCreatedDTO createdDTO, MatchEntity entity) {
        // home team set
        HomeTeamEntity homeTeam = new HomeTeamEntity();
        homeTeam.setHomeTeamName(createdDTO.getHomeTeam().getHomeTeamName());
        homeTeam.setOwnGoal(createdDTO.getHomeTeam().getOwnGoal());
        homeTeam.setPlayedGames(createdDTO.getHomeTeam().getPlayedGames());
        homeTeam.setHomeTeamLogoId(createdDTO.getHomeTeam().getHomeLogoDTO().getTeamsLogoCreatedId()); // set home team logo
        HomeTeamEntity savedHomeTeam = homeTeamRepository.save(homeTeam);
        /////////////////////////////////////////////////////
        // away team set
        AwayTeamEntity awayTeam = new AwayTeamEntity();
        awayTeam.setAwayTeamName(createdDTO.getAwayTeam().getAwayTeamName());
        awayTeam.setAwayGoal(createdDTO.getAwayTeam().getAwayGoal());
        awayTeam.setPlayedGames(createdDTO.getAwayTeam().getPlayedGames());
        awayTeam.setAwayTeamLogoId(createdDTO.getAwayTeam().getAwayTeamsLogoDTO().getTeamsLogoCreatedId()); // set away team logo
        AwayTeamEntity savedAwayTeam = awayTeamRepository.save(awayTeam);
        /////////////////////////////////////////////////////
        // joined in teams entity
        TeamsEntity teamsEntity = new TeamsEntity();
        teamsEntity.setHomeTeamEntity(savedHomeTeam);
        teamsEntity.setAwayTeamEntity(savedAwayTeam);
        teamsEntity.setTeamsMatchEntity(entity);// Parent link
        return teamsEntity;
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
                    homeTeamDTO.setOwnGoal(infoDto.getHomeTeamEntity().getOwnGoal());
                    homeTeamDTO.setPlayedGames(infoDto.getHomeTeamEntity().getPlayedGames());
                    homeTeamDTO.setHomeTeamsLogo(matchLogoService.teamsLogoDTO(infoDto.getHomeTeamEntity().getHomeTeamLogoId()));
                    /////////////////////////////////////////////////////
                    // away dto set
                    AwayTeamDTO awayTeamDTO = new AwayTeamDTO();
                    awayTeamDTO.setAwayTeamId(infoDto.getAwayTeamEntity().getAwayTeamId());
                    awayTeamDTO.setAwayTeamName(infoDto.getAwayTeamEntity().getAwayTeamName());
                    awayTeamDTO.setAwayGoal(infoDto.getAwayTeamEntity().getAwayGoal());
                    awayTeamDTO.setPlayedGames(infoDto.getAwayTeamEntity().getPlayedGames());
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
        homeTeamDTO.setOwnGoal(entity.getHomeTeamEntity().getOwnGoal());
        homeTeamDTO.setPlayedGames(entity.getHomeTeamEntity().getPlayedGames());
        homeTeamDTO.setHomeTeamsLogo(matchLogoService.teamsLogoDTO(homeTeamDTO.getHomeTeamsLogo().getTeamsLogoId()));
        /////////////////////////////////////////////////////
        // away dto set
        AwayTeamDTO awayTeamDTO = new AwayTeamDTO();
        awayTeamDTO.setAwayTeamId(entity.getAwayTeamEntity().getAwayTeamId());
        awayTeamDTO.setAwayTeamName(entity.getAwayTeamEntity().getAwayTeamName());
        awayTeamDTO.setAwayGoal(entity.getAwayTeamEntity().getAwayGoal());
        awayTeamDTO.setPlayedGames(entity.getAwayTeamEntity().getPlayedGames());
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
