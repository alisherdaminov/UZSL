package UZSL.application.dto.clubs.clubs_info.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClubsProfileDTO {

    private String clubsProfileId;
    private String stadiumName;
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
