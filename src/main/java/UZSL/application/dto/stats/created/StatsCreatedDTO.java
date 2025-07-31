package UZSL.application.dto.stats.created;

import lombok.*;

import java.util.List;

@Getter
@Setter
public class StatsCreatedDTO {

    private String statsName;
    private List<StatsPlayersCreatedDTO> createStatsGoal;

}
