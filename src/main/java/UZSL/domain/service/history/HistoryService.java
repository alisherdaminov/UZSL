package UZSL.domain.service.history;

import UZSL.application.dto.history.created.HistoryCreatedDTO;
import UZSL.application.dto.history.dto.HistoryDTO;

import java.util.List;

public interface HistoryService {

    HistoryDTO createHistory(HistoryCreatedDTO historyCreatedDTO);

    List<HistoryDTO> getAllHistoryList();

    HistoryDTO updateHistory(String historyPostId, HistoryCreatedDTO historyCreatedDTO);

    String deleteHistoryById(String historyPostId);
}
