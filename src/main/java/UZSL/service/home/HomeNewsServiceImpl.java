package UZSL.service.home;

import UZSL.config.util.SpringSecurityUtil;
import UZSL.dto.app.AppResponse;
import UZSL.dto.Home.HomeNewsCreatedDTO;
import UZSL.dto.Home.HomeNewsDTO;
import UZSL.dto.extensions.HomeServiceDTO;
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
public class HomeNewsServiceImpl implements HomeNewsService {

    @Autowired
    private HomeNewsRepository homeNewsRepository;
    @Autowired
    private HomeNewsImageService homeNewsImageService;
    @Autowired
    private HomeServiceDTO homeServiceDTO;

    /// CREATE HOME NEWS
    @Override
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
            return homeServiceDTO.createByUserIdPostNewsDTO(entity);
        }
        throw new AppBadException("Unauthorized attempt to create post!");
    }

    /// GET HOME NEWS BY ID
    @Override
    public HomeNewsDTO getByUserIdPostNews(String postNewsId) {
        HomeNewsEntity entity = homeNewsRepository.findById(postNewsId).orElseThrow(() -> new AppBadException("Post news id: " + postNewsId + " is not found!"));
        return homeServiceDTO.createByUserIdPostNewsDTO(entity);
    }

    /// GET ALL HOME NEWS LIST
    @Override
    public PageImpl<HomeNewsDTO> getPostNewsList(int page, int size) {
        PageRequest request = PageRequest.of(page, size);
        Integer userId = SpringSecurityUtil.getCurrentUserId();
        Page<HomeNewsEntity> entityPage = homeNewsRepository.findByUserIdOrderByCreatedAtDesc(userId, request);
        List<HomeNewsDTO> postNewsDTOList = entityPage.getContent().stream().map(homeServiceDTO::getAllByUserIdPostNewsDTO).toList();
        return new PageImpl<>(postNewsDTOList, request, entityPage.getTotalElements());
    }

    /// UPDATE HOME NEWS BY ID AND CREATE NEWS OBJ
    @Override
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
            return homeServiceDTO.updatedContentPostNewsDTO(entity);
        }
        throw new AppBadException("Unauthorized attempt to update this post!");
    }

    /// DELETE HOME NEWS BY ID
    @Override
    public AppResponse<HomeNewsDTO> deletePostNews(String postNewsId) {
        homeNewsRepository.deleteById(postNewsId);
        return new AppResponse<>("Deleted this post news by id: " + postNewsId);
    }

}
