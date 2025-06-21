package UZSL.dto.post_news;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostNewsCreatedDTO {

    private String title;
    private String content;
    private String postImageUrl;
    private String author;
}
