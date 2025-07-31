package UZSL.application.dto.clubs.clubs_info.player.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlayerDTO {

    private String playerId;
    private PlayerDetailDTO playerDetail;
    private PlayerCareerDTO playerCareer;
}
