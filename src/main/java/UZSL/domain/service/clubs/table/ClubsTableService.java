package UZSL.domain.service.clubs.table;

import UZSL.application.dto.clubs.match_info.ClubsTableDTO;
import jakarta.transaction.Transactional;

import java.util.List;

public interface ClubsTableService {

    @Transactional
    void calculateTeamStatsFromMatches(String homeTeamsId, String awayTeamsId);

    List<ClubsTableDTO> getFullClubsTable();
}
