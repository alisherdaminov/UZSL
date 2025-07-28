package UZSL.service.history;

import UZSL.dto.history.created.HistoryCreatedDTO;
import UZSL.dto.history.dto.HistoryDTO;

import java.util.List;

public interface HistoryService {

    HistoryDTO createHistory(HistoryCreatedDTO historyCreatedDTO);

    List<HistoryDTO> getAllHistoryList();

    HistoryDTO updateHistory(String historyPostId, HistoryCreatedDTO historyCreatedDTO);

    String deleteHistoryById(String historyPostId);
}
