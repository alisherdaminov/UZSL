package UZSL.application.dto.Home.image;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HomeImageDTO {
    private String homeImageId;
    private String origenName;
    private String extension;
    private String path;
    private Long size;
    private String url;
    private LocalDateTime createdDate;
}
