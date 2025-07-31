package UZSL.presentation.controller.home;

import UZSL.application.dto.Home.image.HomeImageDTO;
import UZSL.application.dto.app.AppResponse;
import UZSL.application.service.home.images.HomeNewsImageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@RestController
@RequestMapping("/api/v1/post-news-image")
@Tag(name = "Home news image", description = "Admin can have a permission to create, get, update, delete UZSL's Post news image!")
public class HomeImage {

    @Autowired
    private HomeNewsImageService homeNewsImageService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/{userId}")
    public ResponseEntity<AppResponse<HomeImageDTO>> uploadImage(@RequestParam("file") MultipartFile file,
                                                                 @PathVariable("userId") Integer userId) {
        return ResponseEntity.ok().body(new AppResponse<>(homeNewsImageService.uploadImage(file, userId),
                "success", new Date()));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{postId}")
    public ResponseEntity<Resource> downloadImage(@PathVariable("postId") String postId) {
        return homeNewsImageService.downloadImage(postId);
    }
}
