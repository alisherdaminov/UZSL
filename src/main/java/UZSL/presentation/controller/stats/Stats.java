package UZSL.presentation.controller.stats;

import UZSL.application.dto.app.AppResponse;
import UZSL.application.dto.stats.created.StatsCreatedDTO;
import UZSL.application.dto.stats.dto.StatsDTO;
import UZSL.application.service.stats.StatsServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/stats")
@Tag(name = "Stats", description = "Players statistics comprise goals, assists, shots, own goals, penalties")
public class Stats {

    @Autowired
    private StatsServiceImpl service;

    @PostMapping
    public ResponseEntity<AppResponse<StatsDTO>> createStats(@RequestBody StatsCreatedDTO createdDTO) {
        return ResponseEntity.ok().body(new AppResponse<>(service.createStats(createdDTO), "success", new Date()));
    }

    @GetMapping
    public ResponseEntity<AppResponse<List<StatsDTO>>> getAllStats() {
        return ResponseEntity.ok().body(new AppResponse<>(service.getAllStats(), "success", new Date()));
    }

    @GetMapping("/{statsId}")
    public ResponseEntity<AppResponse<StatsDTO>> getStatsById(@PathVariable("statsId") String statsId) {
        return ResponseEntity.ok().body(new AppResponse<>(service.getStatsById(statsId), "success", new Date()));
    }

    @PutMapping("/{statsId}")
    public ResponseEntity<AppResponse<StatsDTO>> updateStats(
            @PathVariable("statsId") String statsId,
            @RequestBody StatsCreatedDTO createdDTO) {
        return ResponseEntity.ok().body(new AppResponse<>(service.updateStats(statsId, createdDTO), "success", new Date()));
    }

    @DeleteMapping("/{statsId}")
    public ResponseEntity<AppResponse<String>> deleteStats(@PathVariable("statsId") String statsId) {
        return ResponseEntity.ok().body(new AppResponse<>(service.deleteStats(statsId), "success", new Date()));
    }
}
