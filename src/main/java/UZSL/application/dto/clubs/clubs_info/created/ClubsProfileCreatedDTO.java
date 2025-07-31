package UZSL.application.dto.clubs.clubs_info.created;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClubsProfileCreatedDTO {

    private String founded;
    private String clubsColor;
    private String street;
    private String city;
    private String direction;
    private String phone;
    private String fax;
    private String websiteName;
    private String emailName;

}
