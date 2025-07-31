package UZSL.application.service.match.logo;

import UZSL.shared.util.SpringSecurityUtil;
import UZSL.application.dto.app.AppResponse;
import UZSL.application.dto.match.teams_logo.TeamsLogoDTO;
import UZSL.domain.model.entity.auth.UserEntity;
import UZSL.domain.model.entity.match.MatchEntity;
import UZSL.domain.model.entity.match.MatchLogoEntity;
import UZSL.shared.enums.UzSlRoles;
import UZSL.shared.exception.AppBadException;
import UZSL.infrastructure.adapter.repository.auth.UserRepository;
import UZSL.infrastructure.adapter.repository.match.MatchLogoRepository;
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
import java.util.Objects;
import java.util.UUID;

@Service
public class MatchLogoService {

    @Autowired
    private MatchLogoRepository matchLogoRepository;
    @Autowired
    private UserRepository userRepository;
    @Value("${attach.upload.folder}")
    private String folderName;
    @Value("${attach.upload.url}")
    private String url;

    public TeamsLogoDTO uploadLogo(MultipartFile file, Integer userId) {
        if (!SpringSecurityUtil.hasRole(UzSlRoles.ROLE_ADMIN) && !userId.equals(SpringSecurityUtil.getCurrentUserId())) {
            throw new AppBadException("You are not allowed to upload images.");
        }
        if (file.isEmpty()) {
            throw new AppBadException("Photo is not found!");
        }
        try {
            String pathFolder = getStringDate();
            String keyUUID = UUID.randomUUID().toString();
            String extension = getExtension(Objects.requireNonNull(file.getOriginalFilename()));

            File folder = new File(folderName + "/" + pathFolder);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            Path path = Paths.get(folderName + "/" + pathFolder + "/" + keyUUID + "." + extension);
            Files.write(path, file.getBytes());

            MatchLogoEntity entity = new MatchLogoEntity();
            entity.setMatchLogoId(keyUUID);
            entity.setPath(pathFolder);
            entity.setExtension(extension);
            entity.setOrigenName(file.getOriginalFilename());
            entity.setSize(file.getSize());
            matchLogoRepository.save(entity);
            return toDTO(entity);
        } catch (IOException e) {
            throw new AppBadException("File saving error: " + e.getMessage());

        }
    }

    public ResponseEntity<Resource> downloadLogo(String matchId) {
        MatchLogoEntity matchLogoEntity = matchLogoRepository.findById(matchId).orElseThrow(() -> new AppBadException("Match not found!"));
        Path filePath = Paths.get(folderName + "/" + matchLogoEntity.getMatchLogoId() + "/" + matchLogoEntity.getMatchLogoId() + "." + matchLogoEntity.getExtension()).normalize();
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

    public boolean updateHomeTeamLogo(String matchId) {
        MatchLogoEntity matchLogoEntity = matchLogoRepository.findById(matchId).orElseThrow(() -> new AppBadException("Home team id: " + matchId + " is not found!"));
        UserEntity userEntity = userRepository.findById(SpringSecurityUtil.getCurrentUserId()).orElseThrow(() -> new AppBadException("User not found!"));
        matchLogoRepository.updateHomeTeamLogo(userEntity.getUserId(), matchLogoEntity.getMatchLogoId());
        File file = new File(folderName + "/" + matchLogoEntity.getPath() + "/" + matchLogoEntity.getMatchLogoId());
        boolean isExisted = false;
        if (file.exists()) {
            isExisted = file.delete();
        }
        return isExisted;
    }

    public boolean updateAwayTeamLogo(String matchId) {
        MatchLogoEntity matchLogoEntity = matchLogoRepository.findById(matchId).orElseThrow(() -> new AppBadException("Away team id: " + matchId + " is not found!"));
        UserEntity userEntity = userRepository.findById(SpringSecurityUtil.getCurrentUserId()).orElseThrow(() -> new AppBadException("User not found!"));
        matchLogoRepository.updateAwayTeamLogo(userEntity.getUserId(), matchLogoEntity.getMatchLogoId());
        File file = new File(folderName + "/" + matchLogoEntity.getPath() + "/" + matchLogoEntity.getMatchLogoId());
        boolean isExisted = false;
        if (file.exists()) {
            isExisted = file.delete();
        }
        return isExisted;
    }

    public AppResponse<String> deleteLogo(String matchId) {
        String removedLogoWithFile = removedLogo(matchId);
        MatchEntity entity = matchLogoRepository.deleteMatchId(removedLogoWithFile);
        return new AppResponse<>("Deleted! " + entity.getMatchId());
    }

    private String removedLogo(String filename) {
        int dotIndex = filename.lastIndexOf(".");
        return (dotIndex > 0) ? filename.substring(0, dotIndex) : filename;
    }

    private TeamsLogoDTO toDTO(MatchLogoEntity entity) {
        TeamsLogoDTO dto = new TeamsLogoDTO();
        dto.setTeamsLogoId(entity.getMatchLogoId());
        dto.setOrigenName(entity.getOrigenName());
        dto.setExtension(entity.getExtension());
        dto.setSize(entity.getSize());
        dto.setUrl(url + "/download/" + entity.getMatchLogoId() + "." + entity.getExtension());
        dto.setCreatedDate(entity.getCreatedAt());
        return dto;
    }

    public TeamsLogoDTO teamsLogoDTO(String matchId) {
        if (matchId == null) return null;
        TeamsLogoDTO dto = new TeamsLogoDTO();
        dto.setTeamsLogoId(matchId);
        dto.setUrl(url + "/download/" + matchId);
        return dto;
    }

    private String getStringDate() {
        return LocalDate.now().toString();
    }

    private String getExtension(String filename) {
        int lastIndex = filename.lastIndexOf(".");
        return filename.substring(lastIndex + 1);
    }

}
