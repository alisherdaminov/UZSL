package UZSL.controller;

import UZSL.config.util.PageUtil;
import UZSL.dto.app.AppResponse;
import UZSL.dto.Home.HomeNewsCreatedDTO;
import UZSL.dto.Home.HomeNewsDTO;
import UZSL.service.home.HomeNewsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@RestController
@RequestMapping("/api/v1/post-news")
@Tag(name = "Home news", description = "Admin can have a permission to create, get, update, delete UZSL's Post news!")
public class Home {

    @Autowired
    private HomeNewsService homeNewsService;

    @PostMapping("/{userId}")
    public ResponseEntity<AppResponse<HomeNewsDTO>> createPostNews(
                                                                   @PathVariable("userId") Integer userId,
                                                                   @RequestBody HomeNewsCreatedDTO createdDTO) {
        return ResponseEntity.ok().body(new AppResponse<>(homeNewsService.createPostNews( userId, createdDTO),
                "success", new Date()));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<AppResponse<HomeNewsDTO>> getByUserIdPostNews(@PathVariable("postId") String postId) {
        return ResponseEntity.ok().body(new AppResponse<>(homeNewsService.getByUserIdPostNews(postId),
                "success", new Date()));
    }

    @GetMapping
    public ResponseEntity<AppResponse<PageImpl<HomeNewsDTO>>> getPostNewsList(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "18") int size) {
        return ResponseEntity.ok().body(new AppResponse<>(homeNewsService.getPostNewsList(PageUtil.page(page), size),
                "success", new Date()));
    }

    @PutMapping("/{postId}")
    public ResponseEntity<AppResponse<HomeNewsDTO>> updatePostNews(@PathVariable("postId") String postId,
                                                                   @RequestBody HomeNewsCreatedDTO createdDTO) {
        return ResponseEntity.ok().body(new AppResponse<>(homeNewsService.updatePostNews(postId, createdDTO),
                "success", new Date()));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<AppResponse<HomeNewsDTO>> updatePostNews(@PathVariable("postId") String postId) {
        return ResponseEntity.ok().body(new AppResponse<>(homeNewsService.deletePostNews(postId),
                "success", new Date()).getData());
    }


}
