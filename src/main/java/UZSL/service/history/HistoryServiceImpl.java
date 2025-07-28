package UZSL.service.history;

import UZSL.dto.history.created.HistoryCreatedDTO;
import UZSL.dto.history.dto.HistoryDTO;
import UZSL.entity.history.HistoryEntity;
import UZSL.exception.AppBadException;
import UZSL.mapper.HistoryMapper;
import UZSL.repository.history.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class HistoryServiceImpl implements HistoryService {

    @Autowired
    private HistoryRepository historyRepository;
    private HistoryMapper historyMapper;

    /// CREATE
    @Override
    public HistoryDTO createHistory(HistoryCreatedDTO historyCreatedDTO) {
        HistoryEntity historyEntity = historyMapper.toHistoryEntity(historyCreatedDTO);
        historyRepository.save(historyEntity);
        return historyMapper.toHistoryDTO(historyEntity);
    }

    /// GET
    @Override
    public List<HistoryDTO> getAllHistoryList() {
        return historyRepository.findAll()
                .stream()
                .map(historyMapper::toHistoryDTO)
                .sorted(Comparator.comparing(HistoryDTO::getCreatedAt).reversed())
                .toList();
    }

    /// UPDATE
    @Override
    public HistoryDTO updateHistory(String historyPostId, HistoryCreatedDTO historyCreatedDTO) {
        HistoryEntity historyEntity = historyRepository.findById(historyPostId).orElseThrow(() -> new AppBadException("History post id is not found!"));
        historyEntity = historyMapper.toUpdateHistoryDTO(historyEntity.getHistoryId(), historyCreatedDTO);
        historyRepository.save(historyEntity);
        return historyMapper.toHistoryDTO(historyEntity);
    }

    /// DELETE
    @Override
    public String deleteHistoryById(String historyPostId) {
        historyRepository.deleteById(historyPostId);
        return "";
    }
}
