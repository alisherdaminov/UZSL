package UZSL.controller.table;

import UZSL.dto.app.AppResponse;
import UZSL.dto.table.ClubsTableDTO;
import UZSL.service.table.ClubsTableServiceImpl;
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
