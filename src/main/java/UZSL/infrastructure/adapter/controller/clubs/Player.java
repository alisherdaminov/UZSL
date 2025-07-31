package UZSL.infrastructure.adapter.controller.clubs;

import UZSL.application.dto.app.AppResponse;
import UZSL.application.dto.clubs.clubs_info.player.created.PlayerCreatedDTO;
import UZSL.application.dto.clubs.clubs_info.player.dto.PlayerDTO;
import UZSL.application.service.clubs.info.player.PlayerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/player-info")
public class Player {

    @Autowired
    private PlayerServiceImpl playerService;

    @PostMapping("/{playerId}")
    public ResponseEntity<AppResponse<PlayerDTO>> createPlayerInfo(
            @PathVariable("playerId") String playerId,
            @RequestBody PlayerCreatedDTO playerCreatedDTO) {
        return ResponseEntity.ok().body(new AppResponse<>(playerService.createPlayerInfo(playerId, playerCreatedDTO), "success", new Date()));
    }

    @GetMapping("/{playerId}")
    public ResponseEntity<AppResponse<List<PlayerDTO>>> getAllPlayerInfo(@PathVariable("playerId") String playerId) {
        return ResponseEntity.ok().body(new AppResponse<>(playerService.getAllPlayerInfo(playerId), "success", new Date()));
    }

    @PutMapping("/{playerId}")
    public ResponseEntity<AppResponse<PlayerDTO>> updatePlayerInfo(
            @PathVariable("playerId") String playerId,
            @RequestBody PlayerCreatedDTO playerCreatedDTO) {
        return ResponseEntity.ok().body(new AppResponse<>(playerService.updatePlayerInfo(playerId, playerCreatedDTO), "success", new Date()));
    }

    @DeleteMapping("/{playerId}")
    public ResponseEntity<AppResponse<String>> deletePlayerInfo(@PathVariable("playerId") String playerId) {
        return ResponseEntity.ok().body(new AppResponse<>(playerService.deletePlayerInfo(playerId), "success", new Date()));
    }
}
