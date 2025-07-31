package UZSL.application.dto.clubs.clubs_info.player.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlayerCareerDTO {

    private String playerCareerId;
    private String appearances;
    private String goals;
    private String assists;
    private String ballActions;
    private String distanceKmForSeason;
    private String penalties;
}
