package UZSL.domain.model.entity.home;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "home_image")
@Getter
@Setter
public class HomeImageEntity {
    @Id
    private String homeImageId;
    @Column(name = "origenName")
    private String origenName;
    @Column(name = "extension")
    private String extension;
    @Column(name = "path")
    private String path;
    @Column(name = "size")
    private Long size;
    @Column(name = "url")
    private String url;
    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    @OneToOne(fetch = FetchType.LAZY)
    private HomeNewsEntity homeNewsEntity;

}
