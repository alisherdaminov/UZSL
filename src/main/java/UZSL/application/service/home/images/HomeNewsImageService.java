package UZSL.application.service.home.images;

import UZSL.shared.util.SpringSecurityUtil;
import UZSL.application.dto.Home.image.HomeImageDTO;
import UZSL.domain.model.entity.auth.UserEntity;
import UZSL.domain.model.entity.home.HomeImageEntity;
import UZSL.shared.enums.UzSlRoles;
import UZSL.shared.exception.AppBadException;
import UZSL.domain.repository.auth.UserRepository;
import UZSL.domain.repository.home.HomeNewsImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * HomeNewsImageService is for storing home new images once given the news
 * this image can be created, got, updated and deleted!
 */
@Service
public class HomeNewsImageService {

    @Autowired
    private HomeNewsImageRepository homeNewsImageRepository;
    @Autowired
    private UserRepository userRepository;
    @Value("${attach.upload.folder}")
    private String folderName;
    @Value("${attach.upload.url}")
    private String url;

    public HomeImageDTO uploadImage(MultipartFile file, Integer userId) {
        if (!SpringSecurityUtil.hasRole(UzSlRoles.ROLE_ADMIN) && !userId.equals(SpringSecurityUtil.getCurrentUserId())) {
            throw new AppBadException("You are not allowed to upload images.");
        }

        if (file.isEmpty()) {
            throw new AppBadException("Photo is not found!");
        }

        try {
            String pathFolder = getDateString();
            String keyUUID = UUID.randomUUID().toString();
            String extension = getExtension(Objects.requireNonNull(file.getOriginalFilename()));

            File folder = new File(folderName + "/" + pathFolder);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            Path path = Paths.get(folderName + "/" + pathFolder + "/" + keyUUID + "." + extension);
            Files.write(path, file.getBytes());

            HomeImageEntity entity = new HomeImageEntity();
            entity.setHomeImageId(keyUUID);
            entity.setPath(pathFolder);
            entity.setExtension(extension);
            entity.setOrigenName(file.getOriginalFilename());
            entity.setSize(file.getSize());
            homeNewsImageRepository.save(entity);
            return toDTO(entity);

        } catch (IOException e) {
            throw new AppBadException("File saving error: " + e.getMessage());
        }
    }


    public ResponseEntity<Resource> downloadImage(String postId) {
        Optional<HomeImageEntity> optional = homeNewsImageRepository.findById(postId);
        if (optional.isEmpty()) {
            throw new AppBadException("Post id: " + postId + " is not found!");
        }
        HomeImageEntity entity = optional.get();
        Path filePath = Paths.get(folderName + "/" + entity.getPath() +
                "/" + entity.getHomeImageId() + "." + entity.getExtension()).normalize();
        Resource resource;
        try {
            resource = new UrlResource(filePath.toUri());
            if (!resource.exists()) {
                throw new AppBadException("Photo is not found!");
            }
            String contentType = Files.probeContentType(filePath);
            if (contentType == null) {
                contentType = "application/octet-stream";
            }
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(resource);
        } catch (IOException e) {
            throw new AppBadException("Error while reading image file: " + e.getMessage());
        }
    }

    public String removeImage(String filename) {
        int dotIndex = filename.lastIndexOf(".");
        return (dotIndex > 0) ? filename.substring(0, dotIndex) : filename;
    }

    public boolean updatePhoto(String postId, Integer userId) {
        Optional<HomeImageEntity> optionalImage = homeNewsImageRepository.findById(postId);
        Optional<UserEntity> optionalUser = userRepository.findById(userId);

        if (optionalImage.isEmpty() && optionalUser.isEmpty()) {
            throw new AppBadException("Image id: " + postId + " or user id: " + userId + " is not found!");
        }
        HomeImageEntity entityImage = optionalImage.get();
        UserEntity entityUser = optionalUser.get();
        homeNewsImageRepository.updatePhoto(entityUser.getUserId(), entityImage.getHomeImageId());
        File file = new File(folderName + "/" + entityImage.getPath() + "/" + entityImage.getHomeImageId());
        boolean isExisted = false;
        if (file.exists()) {
            isExisted = file.delete();
        }
        return isExisted;
    }

    public HomeImageDTO toDTO(HomeImageEntity entity) {
        HomeImageDTO dto = new HomeImageDTO();
        dto.setHomeImageId(entity.getHomeImageId());
        dto.setOrigenName(entity.getOrigenName());
        dto.setExtension(entity.getExtension());
        dto.setSize(entity.getSize());
        dto.setUrl(url + "/download/" + entity.getHomeImageId() + "." + entity.getExtension());
        dto.setCreatedDate(entity.getCreatedAt());
        return dto;
    }


    public HomeImageDTO homeImageDTO(String homeNewsImageId) {
        if (homeNewsImageId == null) return null;
        HomeImageDTO dto = new HomeImageDTO();
        dto.setHomeImageId(homeNewsImageId);
        dto.setUrl(url + "/download/" + homeNewsImageId);
        return dto;
    }

    public String getDateString() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH);
        int day = Calendar.getInstance().get(Calendar.DATE);
        return year + "/" + month + "/" + day;
    }

    private String getDateString2() {
        return LocalDate.now().toString(); // "2025-06-28"
    }

    public String getExtension(String filename) {
        int lastIndex = filename.lastIndexOf(".");
        return filename.substring(lastIndex + 1);
    }
}
