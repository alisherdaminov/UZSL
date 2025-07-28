package UZSL.controller.history;

import UZSL.dto.app.AppResponse;
import UZSL.dto.history.created.HistoryCreatedDTO;
import UZSL.dto.history.dto.HistoryDTO;
import UZSL.service.history.HistoryServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/history")
@Tag(name = "History", description = "Admin can have a permission to create, get, update, delete UZSL's history post!")
public class History {

    @Autowired
    private HistoryServiceImpl service;

    @PostMapping
    public ResponseEntity<AppResponse<HistoryDTO>> createHistory(@RequestBody HistoryCreatedDTO createdDTO) {
        return ResponseEntity.ok().body(new AppResponse<>(service.createHistory(createdDTO), "success", new Date()));
    }

    @GetMapping
    public ResponseEntity<AppResponse<List<HistoryDTO>>> getAllHistoryList() {
        return ResponseEntity.ok().body(new AppResponse<>(service.getAllHistoryList(), "success", new Date()));
    }

    @PutMapping("/{history-postId}")
    public ResponseEntity<AppResponse<HistoryDTO>> updateHistory(
            @PathVariable("history-postId") String historyPostId,
            @RequestBody HistoryCreatedDTO createdDTO) {
        return ResponseEntity.ok().body(new AppResponse<>(service.updateHistory(historyPostId, createdDTO), "success", new Date()));
    }

    @DeleteMapping("/{history-postId}")
    public ResponseEntity<AppResponse<String>> updateHistory(
            @PathVariable("history-postId") String historyPostId) {
        return ResponseEntity.ok().body(new AppResponse<>(service.deleteHistoryById(historyPostId), "success", new Date()));
    }

}
