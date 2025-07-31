package UZSL.application.dto.stats.created;

import lombok.*;

@Getter
@Setter
public class StatsPlayersCreatedDTO {

    private String title;
    private String firstName;
    private String lastName;
    private String clubNumber;
    private int goals;
    private int assist;
    private int shots;
    private int ownGoal;
    private int penalties;
}
