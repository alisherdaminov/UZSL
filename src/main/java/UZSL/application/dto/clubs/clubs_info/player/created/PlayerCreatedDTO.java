package UZSL.application.dto.clubs.clubs_info.player.created;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlayerCreatedDTO {

    private String playerId;
    private PlayerDetailCreatedDTO playerDetailCreated;
    private PlayerCareerCreatedDTO playerCareerCreated;
}
