package UZSL.controller.match;

import UZSL.dto.app.AppResponse;
import UZSL.dto.match.MatchDTO;
import UZSL.dto.match.created.MatchCreatedDTO;
import UZSL.service.match.MatchService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/v1/matches")
@Tag(name = "Matches", description = "Admin can have a permission to create, get, update, delete UZSL's matches with date and time!")
public class Match {

    @Autowired
    private MatchService matchService;

    @PostMapping("/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<AppResponse<MatchDTO>> createMatch(@PathVariable("userId") Integer userId,
                                                     @RequestBody MatchCreatedDTO matchCreatedDTO) {
        return ResponseEntity.ok().body(new AppResponse<>(matchService.createMatch(userId, matchCreatedDTO), "success", new Date()));
    }


}
