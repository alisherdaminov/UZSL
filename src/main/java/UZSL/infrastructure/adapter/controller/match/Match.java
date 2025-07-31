package UZSL.infrastructure.adapter.controller.match;

import UZSL.application.dto.app.AppResponse;
import UZSL.application.dto.match.dto.MatchDTO;
import UZSL.application.dto.match.dto.TeamsDTO;
import UZSL.application.dto.match.created.MatchCreatedDTO;
import UZSL.application.dto.match.teams_logo.updated_logo_created.MatchUpdateLogoCreatedDTO;
import UZSL.application.dto.match.teams_logo.updated_logo_dto.MatchUpdatedLogoDTO;
import UZSL.application.dto.match.updateDTO.dto.MatchUpdatedDTO;
import UZSL.application.dto.match.updateDTO.created.MatchUpdateCreatedDTO;
import UZSL.application.service.match.MatchServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/matches")
@Tag(name = "Matches", description = "UZSL's club's match with date and time can be shown for users! By Admin who can perform all!")
public class Match {

    @Autowired
    private MatchServiceImpl matchService;

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

    @GetMapping("/{teamsId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<AppResponse<TeamsDTO>> getByIdTeamsData(@PathVariable("teamsId") String teamsId) {
        return ResponseEntity.ok().body(new AppResponse<>(matchService.getByIdTeamsData(teamsId), "success", new Date()));
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<AppResponse<List<MatchDTO>>> getByIdMatchesData() {
        return ResponseEntity.ok().body(new AppResponse<>(matchService.getAllMatchesData(), "success", new Date()));
    }

    @PutMapping("/{matchId}/{homeTeamsId}/{awayTeamsId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<AppResponse<MatchUpdatedDTO>> updateMatch(@PathVariable("matchId") String matchId,
                                                                    @PathVariable("homeTeamsId") String homeTeamsId,
                                                                    @PathVariable("awayTeamsId") String awayTeamsId,
                                                                    @RequestBody MatchUpdateCreatedDTO createdDTO) {
        return ResponseEntity.ok().body(new AppResponse<>(matchService.updatedMatch(matchId, homeTeamsId, awayTeamsId, createdDTO), "success", new Date()));
    }

    @PutMapping("/logo/{matchId}/{homeTeamsId}/{awayTeamsId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<AppResponse<MatchUpdatedLogoDTO>> updateMatch(@PathVariable("matchId") String matchId,
                                                                        @PathVariable("homeTeamsId") String homeTeamsId,
                                                                        @PathVariable("awayTeamsId") String awayTeamsId,
                                                                        @RequestBody MatchUpdateLogoCreatedDTO createdDTO) {
        return ResponseEntity.ok().body(new AppResponse<>(matchService.updateClubLogo(matchId, homeTeamsId, awayTeamsId, createdDTO), "success", new Date()));
    }

    @DeleteMapping("/match/{matchId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<AppResponse<String>> deleteMatchById(@PathVariable("matchId") String matchId) {
        return ResponseEntity.ok().body(new AppResponse<>(matchService.deleteMatchById(matchId), "success", new Date()));
    }

    @DeleteMapping("/teams/{teamsId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<AppResponse<String>> deleteTeamsById(@PathVariable("teamsId") String teamsId) {
        return ResponseEntity.ok().body(new AppResponse<>(matchService.deleteTeamsById(teamsId), "success", new Date()));
    }


}
