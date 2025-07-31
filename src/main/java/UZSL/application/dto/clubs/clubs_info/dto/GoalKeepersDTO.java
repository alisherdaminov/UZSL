package UZSL.application.dto.clubs.clubs_info.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GoalKeepersDTO {

    private String clubsGoalKeepersId;
    private String firstName;
    private String lastName;
    private String clubNumber;
}
