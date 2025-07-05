package UZSL.controller.match;

import UZSL.dto.app.AppResponse;
import UZSL.dto.match.image.MatchLogoDTO;
import UZSL.service.match.MatchLogoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@RestController
@RequestMapping("/api/v1/match-logo")
@Tag(name = "Match logo", description = "Admin can have a permission to create, get, update, delete UZSL's match logo!")
public class MatchLogo {

    @Autowired
    private MatchLogoService matchLogoService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/home-team/{userId}")
    public ResponseEntity<AppResponse<MatchLogoDTO>> uploadHomeTeamLogo(@RequestParam("file") MultipartFile file,
                                                                        @PathVariable("userId") Integer userId) {
        return ResponseEntity.ok().body(new AppResponse<>(matchLogoService.uploadLogo(file, userId),
                "success", new Date()));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/visitor-team/{userId}")
    public ResponseEntity<AppResponse<MatchLogoDTO>> uploadVisitorTeamLogo(@RequestParam("file") MultipartFile file,
                                                                           @PathVariable("userId") Integer userId) {
        return ResponseEntity.ok().body(new AppResponse<>(matchLogoService.uploadLogo(file, userId),
                "success", new Date()));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{matchId}")
    public ResponseEntity<Resource> downloadLogo(@PathVariable("matchId") String matchId) {
        return matchLogoService.downloadLogo(matchId);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{matchId}")
    public AppResponse<String> deleteLogo(@PathVariable("matchId") String matchId) {
        return matchLogoService.deleteLogo(matchId);
    }
}
