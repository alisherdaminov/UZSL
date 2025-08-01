package UZSL.application.service.home;

import UZSL.domain.service.home.HomeNewsService;
import UZSL.shared.util.SpringSecurityUtil;
import UZSL.application.dto.app.AppResponse;
import UZSL.application.dto.Home.HomeNewsCreatedDTO;
import UZSL.application.dto.Home.HomeNewsDTO;
import UZSL.application.mapper.HomeMapper;
import UZSL.domain.model.entity.home.HomeNewsEntity;
import UZSL.shared.enums.UzSlRoles;
import UZSL.shared.exception.AppBadException;
import UZSL.domain.repository.home.HomeNewsRepository;
import UZSL.application.service.home.images.HomeNewsImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
/**
 * HomeNewsServiceImpl implements HomeNewsService and override bellow functions these are for creation of UZSL clubs daily news, story
 *HomeNewsImageService is for attaching of the current news image,
 * all news data is showing with some images
 * */
@Service
public class HomeNewsServiceImpl implements HomeNewsService {

    @Autowired
    private HomeNewsRepository homeNewsRepository;
    @Autowired
    private HomeNewsImageService homeNewsImageService;
    @Autowired
    private HomeMapper homeMapper;

    /// CREATE HOME NEWS
    @Override
    public HomeNewsDTO createPostNews(Integer userId, HomeNewsCreatedDTO homeNewsCreatedDTO) {
        Integer currentUser = SpringSecurityUtil.getCurrentUserId();
        if (SpringSecurityUtil.hasRole(UzSlRoles.ROLE_USER) && !userId.equals(currentUser)) {
            throw new AppBadException("You do not have any permission to create this post news!");
        }
        if (SpringSecurityUtil.hasRole(UzSlRoles.ROLE_ADMIN) && userId.equals(currentUser)) {
            HomeNewsEntity entity = homeMapper.toHomeEntity(homeNewsCreatedDTO);
            entity.setUserId(currentUser);
            homeNewsRepository.save(entity);
            return homeMapper.toCreateDTO(entity);
        }
        throw new AppBadException("Unauthorized attempt to create post!");
    }

    /// GET HOME NEWS BY ID
    @Override
    public HomeNewsDTO getByUserIdPostNews(String postNewsId) {
        HomeNewsEntity entity = homeNewsRepository.findById(postNewsId).orElseThrow(() -> new AppBadException("Post news id: " + postNewsId + " is not found!"));
        return homeMapper.toCreateDTO(entity);
    }

    /// GET ALL HOME NEWS LIST
    @Override
    public PageImpl<HomeNewsDTO> getPostNewsList(int page, int size) {
        PageRequest request = PageRequest.of(page, size);
        Integer userId = SpringSecurityUtil.getCurrentUserId();
        Page<HomeNewsEntity> entityPage = homeNewsRepository.findByUserIdOrderByCreatedAtDesc(userId, request);
        List<HomeNewsDTO> postNewsDTOList = entityPage.getContent().stream().map(homeMapper::toUserByIdDTO).toList();
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
            return homeMapper.toUpdateDTO(entity);
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
