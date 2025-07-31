package UZSL.application.dto.stats.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatsPlayersDTO {

    private String statsPlayerId;
    private String title;
    private String firstName;
    private String lastName;
    private String clubNumber;
    private String clubsFullName;
    private int goals;
    private int assist;
    private int shots;
    private int ownGoal;
    private int penalties;
    private LocalDateTime createdAt;

}
