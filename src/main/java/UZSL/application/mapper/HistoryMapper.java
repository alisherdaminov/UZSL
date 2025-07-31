package UZSL.application.mapper;

import UZSL.application.dto.history.created.HistoryCreatedDTO;
import UZSL.application.dto.history.dto.HistoryDTO;
import UZSL.domain.model.entity.history.HistoryEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class HistoryMapper {

    /// ENTITY TO DTO CREATE
    public HistoryEntity toHistoryEntity(HistoryCreatedDTO historyCreatedDTO) {
        return HistoryEntity.builder().
                title(historyCreatedDTO.getTitle()).
                description(historyCreatedDTO.getDescription()).
                createdAt(LocalDateTime.now()).
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

    /// DTO TO ENTITY GET
    public HistoryDTO toHistoryDTO(HistoryEntity historyEntity) {
        return HistoryDTO.builder().
                historyId(historyEntity.getHistoryId()).
                title(historyEntity.getTitle()).
                description(historyEntity.getDescription()).
                createdAt(historyEntity.getCreatedAt()).
                build();
    }

}
