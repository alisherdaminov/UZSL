package UZSL.dto.clubs.clubs_info.created;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MidFieldersCreatedDTO {

    private String firstName;
    private String lastName;
    private String clubNumber;
}
