package UZSL.controller.clubs;

import UZSL.dto.app.AppResponse;
import UZSL.dto.clubs.clubs_info.created.ClubsSquadCreatedDTO;
import UZSL.dto.clubs.clubs_info.dto.ClubsSquadDTO;
import UZSL.service.clubs.info.ClubsInfoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/clubs-info")
public class ClubsInfo {

    @Autowired
    private ClubsInfoServiceImpl clubsInfoService;

    @PostMapping("/{clubsId}")
    public ResponseEntity<AppResponse<ClubsSquadDTO>> createSquadAndProfile(
            @PathVariable("clubsId") String clubsId,
            @RequestBody ClubsSquadCreatedDTO createdDTO) {
        return ResponseEntity.ok().body(new AppResponse<>(clubsInfoService.createSquadAndProfile(clubsId, createdDTO),
                "success", new Date()));
    }

    @GetMapping
    public ResponseEntity<AppResponse<List<ClubsSquadDTO>>> getClubsInfoList() {
        return ResponseEntity.ok().body(new AppResponse<>(clubsInfoService.getClubsInfoList(), "success", new Date()));
    }

    @PutMapping("/{clubsId}/{playerId}")
    public ResponseEntity<AppResponse<ClubsSquadDTO>> updateSquad(
            @PathVariable("clubsId") String clubsId,
            @PathVariable("playerId") String playerId,
            @RequestBody ClubsSquadCreatedDTO createdDTO) {
        return ResponseEntity.ok().body(new AppResponse<>(clubsInfoService.updateSquad(clubsId, playerId, createdDTO),
                "success", new Date()));
    }

    @DeleteMapping("/{clubsId}")
    public ResponseEntity<AppResponse<String>> deleteSquad(@PathVariable("clubsId") String clubsId) {
        return ResponseEntity.ok().body(new AppResponse<>(clubsInfoService.deleteSquad(clubsId),
                "success", new Date()));
    }


}
