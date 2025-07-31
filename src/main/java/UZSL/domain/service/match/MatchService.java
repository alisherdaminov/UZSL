package UZSL.domain.service.match;

import UZSL.application.dto.match.created.MatchCreatedDTO;
import UZSL.application.dto.match.dto.MatchDTO;
import UZSL.application.dto.match.dto.TeamsDTO;
import UZSL.application.dto.match.teams_logo.updated_logo_created.MatchUpdateLogoCreatedDTO;
import UZSL.application.dto.match.teams_logo.updated_logo_dto.MatchUpdatedLogoDTO;
import UZSL.application.dto.match.updateDTO.created.MatchUpdateCreatedDTO;
import UZSL.application.dto.match.updateDTO.dto.MatchUpdatedDTO;

import java.util.List;
public interface MatchService {

    MatchDTO createMatch(Integer userId, MatchCreatedDTO matchCreatedDTO);

    MatchDTO getByIdMatchesData(String matchId);

    TeamsDTO getByIdTeamsData(String teamsId);

    List<MatchDTO> getAllMatchesData();

    MatchUpdatedLogoDTO updateClubLogo(String matchId, String homeTeamsId, String awayTeamsId, MatchUpdateLogoCreatedDTO createdDTO);

    MatchUpdatedDTO updatedMatch(String matchId, String homeTeamsId, String awayTeamsId, MatchUpdateCreatedDTO createdDTO);

    String deleteMatchById(String matchId);

    String deleteTeamsById(String teamsId);
}
