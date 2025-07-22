package UZSL.dto.clubs.clubs_info.player.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlayerCareerDTO {

    private String playerCareerId;
    private String appearances;
    private String goals;
    private String assists;
    private String ballActions;
    private String distanceKmForSeason;
    private String Penalties;
}
