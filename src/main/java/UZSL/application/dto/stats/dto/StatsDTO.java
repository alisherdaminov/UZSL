package UZSL.application.dto.stats.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatsDTO {

    private String statsId;
    private String statsName;
    private List<StatsPlayersDTO> topGoals;
    private List<StatsPlayersDTO> topAssists;
    private List<StatsPlayersDTO> topShots;
    private List<StatsPlayersDTO> topOwnGoals;
    private List<StatsPlayersDTO> topPenalties;
}
