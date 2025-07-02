package UZSL.dto.match;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MatchDTO {

    private String matchId;
    private String matchStartedDate;
    private String matchStartedTime;
    private List<ClubsMatchInfoDTO> clubsMatchInfoDTOList;
}
