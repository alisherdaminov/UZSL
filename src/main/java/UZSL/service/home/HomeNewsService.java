package UZSL.service.home;

import UZSL.config.util.SpringSecurityUtil;
import UZSL.dto.app.AppResponse;
import UZSL.dto.Home.HomeNewsCreatedDTO;
import UZSL.dto.Home.HomeNewsDTO;
import UZSL.entity.home.HomeNewsEntity;
import UZSL.enums.UzSlRoles;
import UZSL.exception.AppBadException;
import UZSL.repository.home.HomeNewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class HomeNewsService {

    @Autowired
    private HomeNewsRepository homeNewsRepository;
    @Autowired
    private HomeNewsImageService homeNewsImageService;

    // create post news
    public HomeNewsDTO createPostNews(Integer userId, HomeNewsCreatedDTO homeNewsCreatedDTO) {
        Integer currentUser = SpringSecurityUtil.getCurrentUserId();
        if (SpringSecurityUtil.hasRole(UzSlRoles.ROLE_USER) && !userId.equals(currentUser)) {
            throw new AppBadException("You do not have any permission to create this post news!");
        }
        if (SpringSecurityUtil.hasRole(UzSlRoles.ROLE_ADMIN) && userId.equals(currentUser)) {
            HomeNewsEntity entity = new HomeNewsEntity();
            entity.setTitle(homeNewsCreatedDTO.getTitle());
            entity.setContent(homeNewsCreatedDTO.getContent());
            entity.setHomeImageId(homeNewsCreatedDTO.getHomeImageCreatedDTO().getHomeImageCreatedId());
            entity.setUserId(currentUser);
            entity.setAuthor(homeNewsCreatedDTO.getAuthor());
            entity.setCreatedAt(LocalDateTime.now());
            homeNewsRepository.save(entity);
            return createByUserIdPostNewsDTO(entity);
        }
        throw new AppBadException("Unauthorized attempt to create post!");
    }

    // with content by user id
    public HomeNewsDTO getByUserIdPostNews(String postNewsId) {
        HomeNewsEntity entity = homeNewsRepository.findById(postNewsId).orElseThrow(() -> new AppBadException("Post news id: " + postNewsId + " is not found!"));
        return createByUserIdPostNewsDTO(entity);
    }

    // Get all data
    public PageImpl<HomeNewsDTO> getPostNewsList(int page, int size) {
        PageRequest request = PageRequest.of(page, size);
        Integer userId = SpringSecurityUtil.getCurrentUserId();
        Page<HomeNewsEntity> entityPage = homeNewsRepository.findByUserIdOrderByCreatedAtDesc(userId, request);
        List<HomeNewsDTO> postNewsDTOList = entityPage.getContent().stream().map(this::getAllByUserIdPostNewsDTO).toList();
        return new PageImpl<>(postNewsDTOList, request, entityPage.getTotalElements());
    }

    // all data update
    public HomeNewsDTO updatePostNews(String postId, Integer userId, HomeNewsCreatedDTO createdDTO) {
        Optional<HomeNewsEntity> optional = homeNewsRepository.findById(postId);
        if (optional.isEmpty()) {
            throw new AppBadException("Post id: " + postId + "is not found!");
        }
        HomeNewsEntity postNewsEntity = optional.get();
        if (SpringSecurityUtil.hasRole(UzSlRoles.ROLE_USER) && !postNewsEntity.getPostNewsId().equals(postId)) {
            throw new AppBadException("You do not have any permission to update this post news!");
        }
        String oldPhotoId = null;
        if (!createdDTO.getHomeImageCreatedDTO().getHomeImageCreatedId().equals(postNewsEntity.getHomeImageId())) {
            oldPhotoId = postNewsEntity.getHomeImageId();
        }
        if (SpringSecurityUtil.hasRole(UzSlRoles.ROLE_ADMIN) && postNewsEntity.getPostNewsId().equals(postId)) {
            HomeNewsEntity entity = new HomeNewsEntity();
            String title = createdDTO.getTitle();
            String content = createdDTO.getContent();
            String author = createdDTO.getAuthor();
            entity.setHomeImageId(createdDTO.getHomeImageCreatedDTO().getHomeImageCreatedId());
            entity.setUpdatedAt(LocalDateTime.now());
            homeNewsRepository.updateHomeNews(title, content, author, postId);
            if (oldPhotoId != null) {
                homeNewsImageService.updatePhoto(oldPhotoId, userId);
            }
            return updatedContentPostNewsDTO(entity);
        }
        throw new AppBadException("Unauthorized attempt to update this post!");
    }

    public AppResponse<HomeNewsDTO> deletePostNews(String postNewsId) {
        homeNewsRepository.deleteById(postNewsId);
        return new AppResponse<>("Deleted this post news by id: " + postNewsId);
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
