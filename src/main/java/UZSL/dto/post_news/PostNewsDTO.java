package UZSL.dto.post_news;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostNewsDTO {

    private String postNewsId;
    private String title;
    private String content;
    private String postImageUrl;
    private String author;
    private LocalDateTime createdAt;
}
