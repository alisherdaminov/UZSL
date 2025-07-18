package UZSL.dto.extensions;

import UZSL.dto.match.created.TeamsCreatedDTO;
import UZSL.dto.match.dto.AwayTeamDTO;
import UZSL.dto.match.dto.HomeTeamDTO;
import UZSL.dto.match.dto.MatchDTO;
import UZSL.dto.match.dto.TeamsDTO;
import UZSL.dto.match.teams_logo.updated_logo_dto.AwayTeamUpdatedLogoDTO;
import UZSL.dto.match.teams_logo.updated_logo_dto.HomeTeamUpdatedLogoDTO;
import UZSL.dto.match.teams_logo.updated_logo_dto.MatchUpdatedLogoDTO;
import UZSL.dto.match.teams_logo.updated_logo_dto.TeamsUpdatedLogoDTO;
import UZSL.dto.match.updateDTO.dto.AwayTeamUpdatedDTO;
import UZSL.dto.match.updateDTO.dto.HomeTeamUpdatedDTO;
import UZSL.dto.match.updateDTO.dto.MatchUpdatedDTO;
import UZSL.dto.match.updateDTO.dto.TeamsUpdatedDTO;
import UZSL.entity.match.AwayTeamEntity;
import UZSL.entity.match.HomeTeamEntity;
import UZSL.entity.match.MatchEntity;
import UZSL.entity.match.TeamsEntity;
import UZSL.exception.AppBadException;
import UZSL.repository.match.AwayTeamRepository;
import UZSL.repository.match.HomeTeamRepository;
import UZSL.repository.match.TeamsRepository;
import UZSL.service.match.MatchLogoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DtoExtensions {

    private final HomeTeamRepository homeTeamRepository;
    private final AwayTeamRepository awayTeamRepository;
    private final TeamsRepository teamsRepository;
    private final MatchLogoService matchLogoService;

    @Autowired
    public DtoExtensions(HomeTeamRepository homeTeamRepository,
                         AwayTeamRepository awayTeamRepository,
                         TeamsRepository teamsRepository,
                         MatchLogoService matchLogoService) {
        this.homeTeamRepository = homeTeamRepository;
        this.awayTeamRepository = awayTeamRepository;
        this.teamsRepository = teamsRepository;
        this.matchLogoService = matchLogoService;
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

    // TO DTO FOR UPDATE CLUBS ALL GOALS
    public MatchUpdatedDTO toUpdateDTO(MatchEntity entity) {
        MatchUpdatedDTO dto = new MatchUpdatedDTO();
        dto.setMatchUpdatedId(entity.getMatchId());
        List<TeamsUpdatedDTO> teamsDTOList = entity.getTeamsEntityList().stream().map(
                infoDto -> {
                    // home dto set
                    HomeTeamUpdatedDTO homeTeamDTO = new HomeTeamUpdatedDTO();
                    homeTeamDTO.setHomeTeamId(infoDto.getHomeTeamEntity().getHomeTeamId());
                    homeTeamDTO.setOwnGoal(infoDto.getHomeTeamEntity().getOwnGoal());
                    /////////////////////////////////////////////////////
                    // away dto set
                    AwayTeamUpdatedDTO awayTeamDTO = new AwayTeamUpdatedDTO();
                    awayTeamDTO.setAwayTeamId(infoDto.getAwayTeamEntity().getAwayTeamId());
                    awayTeamDTO.setAwayGoal(infoDto.getAwayTeamEntity().getAwayGoal());
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


    // TO DTO FOR UPDATE CLUBS LOGO
    public MatchUpdatedLogoDTO toUpdateClubLogoDTO(MatchEntity entity) {
        MatchUpdatedLogoDTO dto = new MatchUpdatedLogoDTO();
        dto.setMatchUpdatedId(entity.getMatchId());
        List<TeamsUpdatedLogoDTO> teamsDTOList = entity.getTeamsEntityList().stream().map(
                infoDto -> {
                    // home dto set
                    HomeTeamUpdatedLogoDTO homeTeamDTO = new HomeTeamUpdatedLogoDTO();
                    homeTeamDTO.setHomeTeamId(infoDto.getHomeTeamEntity().getHomeTeamId());
                    homeTeamDTO.setHomeTeamsLogo(matchLogoService.teamsLogoDTO(infoDto.getHomeTeamEntity().getHomeTeamLogoId()));
                    /////////////////////////////////////////////////////
                    // away dto set
                    AwayTeamUpdatedLogoDTO awayTeamDTO = new AwayTeamUpdatedLogoDTO();
                    awayTeamDTO.setAwayTeamId(infoDto.getAwayTeamEntity().getAwayTeamId());
                    awayTeamDTO.setAwayTeamsLogo(matchLogoService.teamsLogoDTO(infoDto.getAwayTeamEntity().getAwayTeamLogoId()));
                    /////////////////////////////////////////////////////
                    // set teams dto
                    TeamsUpdatedLogoDTO teamsDTO = new TeamsUpdatedLogoDTO();
                    teamsDTO.setTeamsId(infoDto.getTeamsId());
                    teamsDTO.setHomeTeam(homeTeamDTO);
                    teamsDTO.setAwayTeam(awayTeamDTO);
                    return teamsDTO;
                }
        ).collect(Collectors.toList());
        dto.setTeamsUpdatedList(teamsDTOList);
        return dto;
    }

    // TO MATCHES DTO
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

    // TO TEAMS DTO
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
