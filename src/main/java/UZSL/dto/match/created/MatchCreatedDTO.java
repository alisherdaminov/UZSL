package UZSL.dto.match.created;

import UZSL.dto.match.image.MatchLogoCreatedDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MatchCreatedDTO {

    private String matchStartedDate;
    private String matchStartedTime;
    private List<ClubsMatchInfoCreatedDTO> clubsMatchInfoCreatedDTO;
    private MatchLogoCreatedDTO matchLogoCreatedDTO;
}
