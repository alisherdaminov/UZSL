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
import java.util.List;

@RestController
@RequestMapping("/api/v1/matches")
@Tag(name = "Matches", description = "Admin can have a permission to create, get, update, delete UZSL's matches with date and time!")
public class Match {

    @Autowired
    private MatchService matchService;

    @PostMapping("/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<AppResponse<MatchDTO>> createMatch(@PathVariable("userId") Integer userId, @RequestBody MatchCreatedDTO matchCreatedDTO) {
        return ResponseEntity.ok().body(new AppResponse<>(matchService.createMatch(userId, matchCreatedDTO), "success", new Date()));
    }

    @GetMapping("/{matchId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<AppResponse<MatchDTO>> getByIdMatchesData(@PathVariable("matchId") String matchId) {
        return ResponseEntity.ok().body(new AppResponse<>(matchService.getByIdMatchesData(matchId), "success", new Date()));
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<AppResponse<List<MatchDTO>>> getByIdMatchesData() {
        return ResponseEntity.ok().body(new AppResponse<>(matchService.getAllMatchesData(), "success", new Date()));
    }

    @PutMapping("/{matchId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<AppResponse<MatchDTO>> updateMatch(@PathVariable("matchId") String matchId, @RequestBody MatchCreatedDTO createdDTO) {
        return ResponseEntity.ok().body(new AppResponse<>(matchService.updateMatch(matchId, createdDTO), "success", new Date()));
    }

    @DeleteMapping("/{matchId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<AppResponse<String>> deleteMatchById(@PathVariable("matchId") String matchId) {
        return ResponseEntity.ok().body(new AppResponse<>(matchService.deleteMatchById(matchId), "success", new Date()));
    }


}
