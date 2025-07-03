package UZSL.service.match;

import UZSL.config.util.SpringSecurityUtil;
import UZSL.dto.match.ClubsMatchInfoDTO;
import UZSL.dto.match.MatchDTO;
import UZSL.dto.match.created.ClubsMatchInfoCreatedDTO;
import UZSL.dto.match.created.MatchCreatedDTO;
import UZSL.entity.match.ClubsMatchInfoEntity;
import UZSL.entity.match.MatchEntity;
import UZSL.enums.UzSlRoles;
import UZSL.exception.AppBadException;
import UZSL.repository.match.ClubsMatchInfoRepository;
import UZSL.repository.match.MatchRepository;
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
    private ClubsMatchInfoRepository clubsMatchInfoRepository;

    public MatchDTO createMatch(Integer userId, MatchCreatedDTO matchCreatedDTO) {
        Integer currentUser = SpringSecurityUtil.getCurrentUserId();
        if (!SpringSecurityUtil.hasRole(UzSlRoles.ROLE_ADMIN) && !userId.equals(currentUser)) {
            throw new AppBadException("You are not allowed to make the matches!");
        }
        if (SpringSecurityUtil.hasRole(UzSlRoles.ROLE_ADMIN) && userId.equals(currentUser)) {
            MatchEntity entity = new MatchEntity();
            entity.setMatchStartedDate(matchCreatedDTO.getMatchStartedDate());
            entity.setMatchStartedTime(matchCreatedDTO.getMatchStartedTime());
            entity.setUserId(currentUser);
            MatchEntity savedMatches = matchRepository.save(entity);
            List<ClubsMatchInfoEntity> clubsMatchInfoEntityList = new ArrayList<>();
            if (matchCreatedDTO.getClubsMatchInfoCreatedDTO() != null) {
                clubsMatchInfoEntityList = matchCreatedDTO.getClubsMatchInfoCreatedDTO().stream().map(
                                clubsInfo -> toClubsMatchInfoEntity(clubsInfo, savedMatches))
                        .collect(Collectors.toList());
            }
            entity.setClubsMatchInfoList(clubsMatchInfoEntityList);
            return toDTO(entity);
        }
        throw new AppBadException("Unauthorized attempt to create post");
    }

    public MatchDTO getByIdMatchesData(String matchId) {
        MatchEntity entity = matchRepository.findById(matchId).orElseThrow(() -> new AppBadException("Match id: " + matchId + " is not found!"));
        return toDTO(entity);
    }

    public List<MatchDTO> getAllMatchesData() {
        List<MatchEntity> entityList = matchRepository.findAll();
        return entityList.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public MatchDTO updateMatch(String matchId, MatchCreatedDTO createdDTO) {
        MatchEntity entity = matchRepository.findById(matchId).orElseThrow(() -> new AppBadException("Match not found!"));
        entity.setMatchStartedDate(createdDTO.getMatchStartedDate());
        entity.setMatchStartedTime(createdDTO.getMatchStartedTime());
        List<ClubsMatchInfoEntity> updatedList = createdDTO.getClubsMatchInfoCreatedDTO().stream().map(
                info -> {
                    ClubsMatchInfoEntity club = new ClubsMatchInfoEntity();
                    club.setVisitorTeamName(info.getVisitorTeamName());
                    club.setHomeTeamGoalNumber(info.getHomeTeamGoalNumber());
                    club.setVisitorTeamGoalNumber(info.getVisitorTeamGoalNumber());
                    club.setVisitorTeamName(info.getVisitorTeamName());
                    club.setMatchEntity(entity);
                    return club;
                }).collect(Collectors.toList());
        entity.setClubsMatchInfoList(updatedList);
        matchRepository.save(entity);
        return toDTO(entity);
    }

    public String deleteMatchById(String matchId) {
        matchRepository.deleteById(matchId);
        return "Deleted: " + matchId;
    }

    public ClubsMatchInfoEntity toClubsMatchInfoEntity(ClubsMatchInfoCreatedDTO createdDTO, MatchEntity entity) {
        ClubsMatchInfoEntity clubsEntity = new ClubsMatchInfoEntity();
        clubsEntity.setHomeTeamName(createdDTO.getHomeTeamName());
        clubsEntity.setHomeTeamLogo(createdDTO.getHomeTeamLogo());
        clubsEntity.setHomeTeamGoalNumber(createdDTO.getHomeTeamGoalNumber());
        clubsEntity.setVisitorTeamGoalNumber(createdDTO.getVisitorTeamGoalNumber());
        clubsEntity.setVisitorTeamLogo(createdDTO.getVisitorTeamLogo());
        clubsEntity.setVisitorTeamName(createdDTO.getVisitorTeamName());
        clubsEntity.setMatchEntity(entity);// Parent link
        clubsMatchInfoRepository.save(clubsEntity);
        return clubsEntity;
    }

    public MatchDTO toDTO(MatchEntity entity) {
        MatchDTO dto = new MatchDTO();
        dto.setMatchId(entity.getMatchId());
        dto.setMatchStartedDate(entity.getMatchStartedDate());
        dto.setMatchStartedTime(entity.getMatchStartedTime());
        List<ClubsMatchInfoDTO> matchInfoDTOList = entity.getClubsMatchInfoList().stream().map(
                infoDto -> {
                    ClubsMatchInfoDTO clubsMatchInfoDTO = new ClubsMatchInfoDTO();
                    clubsMatchInfoDTO.setClubsMatchInfoId(infoDto.getClubsMatchInfoId());
                    clubsMatchInfoDTO.setHomeTeamName(infoDto.getHomeTeamName());
                    clubsMatchInfoDTO.setHomeTeamLogo(infoDto.getHomeTeamLogo());
                    clubsMatchInfoDTO.setHomeTeamGoalNumber(infoDto.getHomeTeamGoalNumber());
                    clubsMatchInfoDTO.setVisitorTeamGoalNumber(infoDto.getVisitorTeamGoalNumber());
                    clubsMatchInfoDTO.setVisitorTeamLogo(infoDto.getVisitorTeamLogo());
                    clubsMatchInfoDTO.setVisitorTeamName(infoDto.getVisitorTeamName());
                    return clubsMatchInfoDTO;
                }
        ).collect(Collectors.toList());
        dto.setClubsMatchInfoDTOList(matchInfoDTOList);
        return dto;
    }

}
