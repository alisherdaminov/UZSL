package UZSL.dto.extensions;

import UZSL.dto.Home.HomeNewsDTO;
import UZSL.entity.home.HomeNewsEntity;
import UZSL.repository.home.HomeNewsRepository;
import UZSL.service.home.images.HomeNewsImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HomeServiceDTO {
    private final HomeNewsRepository homeNewsRepository;
    private final HomeNewsImageService homeNewsImageService;

    @Autowired
    public HomeServiceDTO(HomeNewsRepository homeNewsRepository, HomeNewsImageService homeNewsImageService) {
        this.homeNewsRepository = homeNewsRepository;
        this.homeNewsImageService = homeNewsImageService;
    }

    // all data DTO
    public HomeNewsDTO getAllByUserIdPostNewsDTO(HomeNewsEntity entity) {
        HomeNewsDTO dto = new HomeNewsDTO();
        dto.setPostNewsId(entity.getPostNewsId());
        dto.setTitle(entity.getTitle());
        dto.setHomeImageDTO(homeNewsImageService.homeImageDTO(entity.getPostNewsId()));
        dto.setAuthor(entity.getAuthor());
        dto.setCreatedAt(entity.getCreatedAt());
        return dto;
    }

    // updated content DTO
    public HomeNewsDTO updatedContentPostNewsDTO(HomeNewsEntity entity) {
        HomeNewsDTO dto = new HomeNewsDTO();
        dto.setPostNewsId(entity.getPostNewsId());
        dto.setTitle(entity.getTitle());
        dto.setHomeImageDTO(homeNewsImageService.homeImageDTO(entity.getPostNewsId()));
        dto.setAuthor(entity.getAuthor());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }

    // with content DTO
    public HomeNewsDTO createByUserIdPostNewsDTO(HomeNewsEntity entity) {
        HomeNewsDTO dto = new HomeNewsDTO();
        dto.setPostNewsId(entity.getPostNewsId());
        dto.setTitle(entity.getTitle());
        dto.setContent(entity.getContent());
        dto.setHomeImageDTO(homeNewsImageService.homeImageDTO(entity.getPostNewsId()));
        dto.setAuthor(entity.getAuthor());
        dto.setCreatedAt(entity.getCreatedAt());
        return dto;
    }
}
