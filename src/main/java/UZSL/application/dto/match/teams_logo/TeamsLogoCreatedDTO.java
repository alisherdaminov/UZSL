package UZSL.application.dto.match.teams_logo;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamsLogoCreatedDTO {
    @NotBlank(message = "Id is required")
    private String teamsLogoCreatedId;
}
