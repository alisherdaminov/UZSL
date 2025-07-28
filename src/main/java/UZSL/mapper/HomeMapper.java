package UZSL.mapper;

import UZSL.dto.Home.HomeNewsCreatedDTO;
import UZSL.dto.Home.HomeNewsDTO;
import UZSL.entity.home.HomeNewsEntity;
import UZSL.service.home.images.HomeNewsImageService;

import java.time.LocalDateTime;

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
