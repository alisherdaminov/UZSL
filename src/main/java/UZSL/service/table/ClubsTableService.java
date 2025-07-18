package UZSL.service.table;

import UZSL.dto.table.ClubsTableDTO;
import jakarta.transaction.Transactional;

import java.util.List;

public interface ClubsTableService {

    @Transactional
    void calculateTeamStatsFromMatches(String homeTeamsId, String awayTeamsId);

    List<ClubsTableDTO> getFullClubsTable();
}
