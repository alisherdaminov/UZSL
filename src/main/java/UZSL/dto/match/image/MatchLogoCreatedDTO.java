package UZSL.dto.match.image;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MatchLogoCreatedDTO {
    @NotBlank(message = "Id is required")
    private String matchLogoCreatedId;
}
