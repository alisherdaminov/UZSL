package UZSL.domain.service.stats;

import UZSL.application.dto.stats.created.StatsCreatedDTO;
import UZSL.application.dto.stats.dto.StatsDTO;

import java.util.List;

public interface StatsService {

    StatsDTO createStats(StatsCreatedDTO createdDTO);

    List<StatsDTO> getAllStats();

    StatsDTO updateStats(String statesId, StatsCreatedDTO createdDTO);

    String deleteStats(String statesId);
}
