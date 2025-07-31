package UZSL.application.dto.Home;

import UZSL.application.dto.Home.image.HomeImageDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HomeNewsDTO {

    private String postNewsId;
    private String title;
    private String content;
    private HomeImageDTO homeImageDTO;
    private String author;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
