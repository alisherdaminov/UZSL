package UZSL.application.dto.match.updateDTO.created;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamsUpdateCreatedDTO {

    private HomeTeamUpdateCreatedDTO homeTeamUpdateCreatedDTO;
    private AwayTeamUpdateCreatedDTO awayTeamUpdateCreatedDTO;
}
