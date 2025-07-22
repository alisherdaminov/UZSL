package UZSL.dto.clubs.clubs_info.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StrikersDTO {

    private String clubsStrikersId;
    private String firstName;
    private String lastName;
    private String clubNumber;
}
