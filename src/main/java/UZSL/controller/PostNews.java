package UZSL.controller;

import UZSL.config.util.PageUtil;
import UZSL.dto.app_response.AppResponse;
import UZSL.dto.post_news.PostNewsCreatedDTO;
import UZSL.dto.post_news.PostNewsDTO;
import UZSL.service.post_news.PostNewsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/v1/post-news")
@Tag(name = "Post news", description = "Admin can have a permission to create, get, update, delete UZSL's Post news!")
public class PostNews {

    @Autowired
    private PostNewsService postNewsService;

    @PostMapping("/{userId}")
    public ResponseEntity<AppResponse<PostNewsDTO>> createPostNews(@PathVariable("userId") Integer userId,
                                                                   @RequestBody PostNewsCreatedDTO createdDTO) {
        return ResponseEntity.ok().body(new AppResponse<>(postNewsService.createPostNews(userId, createdDTO),
                "success", new Date()));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<AppResponse<PostNewsDTO>> getByUserIdPostNews(@PathVariable("postId") String postId) {
        return ResponseEntity.ok().body(new AppResponse<>(postNewsService.getByUserIdPostNews(postId),
                "success", new Date()));
    }

    @GetMapping
    public ResponseEntity<AppResponse<PageImpl<PostNewsDTO>>> getPostNewsList(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "18") int size) {
        return ResponseEntity.ok().body(new AppResponse<>(postNewsService.getPostNewsList(PageUtil.page(page), size),
                "success", new Date()));
    }

    @PutMapping("/{postId}")
    public ResponseEntity<AppResponse<PostNewsDTO>> updatePostNews(@PathVariable("postId") String postId,
                                                                   @RequestBody PostNewsCreatedDTO createdDTO) {
        return ResponseEntity.ok().body(new AppResponse<>(postNewsService.updatePostNews(postId, createdDTO),
                "success", new Date()));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<AppResponse<PostNewsDTO>> updatePostNews(@PathVariable("postId") String postId) {
        return ResponseEntity.ok().body(new AppResponse<>(postNewsService.deletePostNews(postId),
                "success", new Date()).getData());
    }


}
