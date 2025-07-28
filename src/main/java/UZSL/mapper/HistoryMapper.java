package UZSL.mapper;

import UZSL.dto.history.created.HistoryCreatedDTO;
import UZSL.dto.history.dto.HistoryDTO;
import UZSL.entity.history.HistoryEntity;

import java.time.LocalDateTime;

public class HistoryMapper {

    /// ENTITY TO DTO CREATE
    public HistoryEntity toHistoryEntity(HistoryCreatedDTO historyCreatedDTO) {
        return HistoryEntity.builder().
                title(historyCreatedDTO.getTitle()).
                description(historyCreatedDTO.getDescription()).
                createdAt(LocalDateTime.now()).
                build();
    }

    /// DTO TO ENTITY GET
    public HistoryDTO toHistoryDTO(HistoryEntity historyEntity) {
        return HistoryDTO.builder().
                historyId(historyEntity.getHistoryId()).
                title(historyEntity.getTitle()).
                description(historyEntity.getDescription()).
                createdAt(historyEntity.getCreatedAt()).
                build();
    }

    /// ENTITY TO DTO UPDATE
    public HistoryEntity toUpdateHistoryDTO(String historyPostId, HistoryCreatedDTO historyCreatedDTO) {
        return HistoryEntity.builder().
                historyId(historyPostId).
                title(historyCreatedDTO.getTitle()).
                description(historyCreatedDTO.getDescription()).
                createdAt(LocalDateTime.now()).
                build();
    }

}
