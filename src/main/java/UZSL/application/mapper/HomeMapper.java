package UZSL.application.mapper;

import UZSL.application.dto.Home.HomeNewsCreatedDTO;
import UZSL.application.dto.Home.HomeNewsDTO;
import UZSL.domain.model.entity.home.HomeNewsEntity;
import UZSL.application.service.home.images.HomeNewsImageService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class HomeMapper {

    private HomeNewsImageService homeNewsImageService;

    /// ENTITY TO DTO
    public HomeNewsEntity toHomeEntity(HomeNewsCreatedDTO home) {
        return HomeNewsEntity.builder().
                title(home.getTitle()).
                content(home.getContent()).
                homeImageId(home.getHomeImageCreatedDTO().getHomeImageCreatedId()).
                author(home.getAuthor()).
                createdAt(LocalDateTime.now()).
                build();
    }

    /// DTO TO ENTITY
    public HomeNewsDTO toCreateDTO(HomeNewsEntity entity) {
        return HomeNewsDTO.builder().
                postNewsId(entity.getPostNewsId()).
                title(entity.getTitle()).
                content(entity.getContent()).
                homeImageDTO(homeNewsImageService.homeImageDTO(entity.getPostNewsId())).
                author(entity.getAuthor()).
                createdAt(entity.getCreatedAt()).
                build();

    }

    public HomeNewsDTO toUserByIdDTO(HomeNewsEntity entity) {
        return HomeNewsDTO.builder()
                .postNewsId(entity.getPostNewsId())
                .title(entity.getTitle())
                .homeImageDTO(homeNewsImageService.homeImageDTO(entity.getPostNewsId()))
                .author(entity.getAuthor())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    public HomeNewsDTO toUpdateDTO(HomeNewsEntity entity) {
        return HomeNewsDTO.builder().
                postNewsId(entity.getPostNewsId()).
                title(entity.getTitle()).
                content(entity.getContent()).
                homeImageDTO(homeNewsImageService.homeImageDTO(entity.getPostNewsId())).
                author(entity.getAuthor()).
                updatedAt(entity.getUpdatedAt()).
                build();
    }

}
