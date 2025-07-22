package UZSL.dto.clubs.clubs_info.player.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlayerDetailDTO {

    private String playerDetailId;
    private String fouls;
    private String yellowCards;
    private String appearances;
    private String sprints;
    private String intensiveRuns;
    private String distanceKm;
    private String speedKmH;
    private String crosses;
}
