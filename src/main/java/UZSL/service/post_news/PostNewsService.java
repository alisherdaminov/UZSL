package UZSL.service.post_news;

import UZSL.config.util.SpringSecurityUtil;
import UZSL.dto.app_response.AppResponse;
import UZSL.dto.post_news.PostNewsCreatedDTO;
import UZSL.dto.post_news.PostNewsDTO;
import UZSL.entity.post_news.PostNewsEntity;
import UZSL.enums.UzSlRoles;
import UZSL.exception.AppBadException;
import UZSL.repository.post_news.PostNewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostNewsService {

    @Autowired
    private PostNewsRepository postNewsRepository;

    // create post news
    public PostNewsDTO createPostNews(Integer userId, PostNewsCreatedDTO postNewsCreatedDTO) {
        Integer currentUser = SpringSecurityUtil.getCurrentUserId();
        if (SpringSecurityUtil.hasRole(UzSlRoles.ROLE_USER) && !userId.equals(currentUser)) {
            throw new AppBadException("You do not have any permission to create this post news!");
        }
        if (SpringSecurityUtil.hasRole(UzSlRoles.ROLE_ADMIN) && userId.equals(currentUser)) {
            PostNewsEntity entity = new PostNewsEntity();
            entity.setTitle(postNewsCreatedDTO.getTitle());
            entity.setContent(postNewsCreatedDTO.getContent());
            // entity.setPostImageUrl();
            entity.setUserId(currentUser);
            entity.setAuthor(postNewsCreatedDTO.getAuthor());
            entity.setCreatedAt(LocalDateTime.now());
            postNewsRepository.save(entity);
            return createWithoutContentPostNewsDTO(entity);
        }
        throw new AppBadException("Unauthorized attempt to create post!");
    }

    // with content by user id
    public PostNewsDTO getByUserIdPostNews(String postNewsId) {
        PostNewsEntity entity = postNewsRepository.findById(postNewsId).orElseThrow(() -> new AppBadException("Post news id: " + postNewsId + " is not found!"));
        return createByUserIdPostNewsDTO(entity);
    }

    // Get all data
    public PageImpl<PostNewsDTO> getPostNewsList(int page, int size) {
        PageRequest request = PageRequest.of(page, size);
        Integer userId = SpringSecurityUtil.getCurrentUserId();
        Page<PostNewsEntity> entityPage = postNewsRepository.findByUserIdAndOrderByCreatedAtDesc(userId, request);
        List<PostNewsDTO> postNewsDTOList = entityPage.getContent().stream().map(this::getAllByUserIdPostNewsDTO).toList();
        return new PageImpl<>(postNewsDTOList, request, entityPage.getTotalElements());
    }

    // all data update
    public PostNewsDTO updatePostNews(String postId, PostNewsCreatedDTO createdDTO) {
        Optional<PostNewsEntity> optional = postNewsRepository.findById(postId);
        if (optional.isEmpty()) {
            throw new AppBadException("Post id: " + postId + "is not found!");
        }
        PostNewsEntity postNewsEntity = optional.get();
        if (SpringSecurityUtil.hasRole(UzSlRoles.ROLE_USER) && !postNewsEntity.getPostNewsId().equals(postId)) {
            throw new AppBadException("You do not have any permission to update this post news!");
        }
        if (SpringSecurityUtil.hasRole(UzSlRoles.ROLE_ADMIN) && postNewsEntity.getPostNewsId().equals(postId)) {
            PostNewsEntity entity = new PostNewsEntity();
            entity.setTitle(createdDTO.getTitle());
            entity.setContent(createdDTO.getContent());
            // entity.setPostImageUrl();
            entity.setUserId(SpringSecurityUtil.getCurrentUserId());
            entity.setAuthor(createdDTO.getAuthor());
            entity.setCreatedAt(LocalDateTime.now());
            postNewsRepository.save(entity);
            return createByUserIdPostNewsDTO(entity);
        }
        throw new AppBadException("Unauthorized attempt to update this post!");
    }

    public AppResponse<PostNewsDTO> deletePostNews(String postNewsId) {
        postNewsRepository.deleteById(postNewsId);
        return new AppResponse<>("Deleted this id: " + postNewsId);
    }

    // all data DTO
    public PostNewsDTO getAllByUserIdPostNewsDTO(PostNewsEntity entity) {
        PostNewsDTO dto = new PostNewsDTO();
        dto.setPostNewsId(entity.getPostNewsId());
        dto.setTitle(entity.getTitle());
        dto.setContent(entity.getContent());
        // dto.setPostImageUrl();
        dto.setAuthor(entity.getAuthor());
        dto.setCreatedAt(entity.getCreatedAt());
        return dto;
    }

    // with content DTO
    public PostNewsDTO createByUserIdPostNewsDTO(PostNewsEntity entity) {
        PostNewsDTO dto = new PostNewsDTO();
        dto.setPostNewsId(entity.getPostNewsId());
        dto.setTitle(entity.getTitle());
        dto.setContent(entity.getContent());
        // dto.setPostImageUrl();
        dto.setAuthor(entity.getAuthor());
        dto.setCreatedAt(entity.getCreatedAt());
        return dto;
    }

    // without content DTO
    public PostNewsDTO createWithoutContentPostNewsDTO(PostNewsEntity entity) {
        PostNewsDTO dto = new PostNewsDTO();
        dto.setPostNewsId(entity.getPostNewsId());
        dto.setTitle(entity.getTitle());
        // dto.setPostImageUrl();
        dto.setAuthor(entity.getAuthor());
        dto.setCreatedAt(entity.getCreatedAt());
        return dto;
    }


}
