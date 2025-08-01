package UZSL.domain.service.stats;

import UZSL.application.dto.stats.created.StatsCreatedDTO;
import UZSL.application.dto.stats.dto.StatsDTO;
import UZSL.application.dto.stats.dto.StatsPlayersDTO;

import java.util.List;

public interface StatsService {

    StatsDTO createStats(StatsCreatedDTO createdDTO);

    List<StatsDTO> getAllStats();

    StatsDTO getStatsById(String playerId);

    StatsDTO updateStats(String statesId, StatsCreatedDTO createdDTO);

    String deleteStats(String statesId);
}
