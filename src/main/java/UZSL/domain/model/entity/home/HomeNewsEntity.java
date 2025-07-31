package UZSL.domain.model.entity.home;

import UZSL.domain.model.entity.auth.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "uzSl_post")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HomeNewsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String postNewsId;
    @Column(name = "title")
    private String title;
    @Column(name = "content")
    private String content;
    @Column(name = "author")
    private String author;
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    // uz_sl_admin
    @Column(name = "user_id")
    private Integer userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private UserEntity userEntity;


    @Column(name = "home_image_id")
    private String homeImageId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "home_image_id", insertable = false, updatable = false)
    private HomeImageEntity homeImageEntityList;

}
