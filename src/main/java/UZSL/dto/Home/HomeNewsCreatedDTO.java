package UZSL.dto.Home;

import UZSL.dto.Home.image.HomeImageCreatedDTO;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HomeNewsCreatedDTO {

    private String title;
    private String content;
    @NotNull(message = "Home image is required")
    private HomeImageCreatedDTO homeImageCreatedDTO;
    private String author;
}
