package UZSL.application.dto.Home.image;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HomeImageCreatedDTO {
    @NotBlank(message = "Id is required")
    private String homeImageCreatedId;
}
