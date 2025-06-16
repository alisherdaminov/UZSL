package UZSL.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "uzSl_post")
@Getter
@Setter
public class UzSLPostNewsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String uzSlPostId;
    @Column(name = "title")
    private String title;
    @Column(name = "content")
    private String content;
    @Column(name = "image")
    private String postImage;
    @Column(name = "author")
    private String author;
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uz_sl_category_id", insertable = false, updatable = false)
    private AdminEntity adminEntity;

}
