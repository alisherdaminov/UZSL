package UZSL.infrastructure.adapter.controller.clubs;

import UZSL.application.dto.app.AppResponse;
import UZSL.application.dto.clubs.match_info.ClubsTableDTO;
import UZSL.application.service.clubs.table.ClubsTableServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/clubs-table")
public class ClubsTable {

    @Autowired
    private ClubsTableServiceImpl clubsTableService;

    @GetMapping
    public ResponseEntity<AppResponse<List<ClubsTableDTO>>> getFullClubsTable() {
        return ResponseEntity.ok().body(new AppResponse<>(clubsTableService.getFullClubsTable(), "success", new Date()));
    }
}
