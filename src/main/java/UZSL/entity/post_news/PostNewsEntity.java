package UZSL.entity.post_news;

import UZSL.entity.auth.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "uzSl_post")
@Getter
@Setter
public class PostNewsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String postNewsId;
    @Column(name = "title")
    private String title;
    @Column(name = "content")
    private String content;
    @Column(name = "post_image_url")
    private String postImageUrl;
    @Column(name = "author")
    private String author;
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    // uz_sl_admin
    @Column(name = "user_id")
    private Integer userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private UserEntity userEntity;

}
