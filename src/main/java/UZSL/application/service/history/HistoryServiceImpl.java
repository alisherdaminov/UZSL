package UZSL.application.service.history;

import UZSL.application.dto.history.created.HistoryCreatedDTO;
import UZSL.application.dto.history.dto.HistoryDTO;
import UZSL.domain.model.entity.history.HistoryEntity;
import UZSL.domain.service.history.HistoryService;
import UZSL.shared.exception.AppBadException;
import UZSL.application.mapper.HistoryMapper;
import UZSL.domain.repository.history.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

/**
 * HistoryServiceImpl implements HistoryService and override bellow functions these are for creation of UZSL clubs history, story, amazing moments
 * Each clubs can have chance to announce its own legendary football players ever! once giving their clubs info to UZSL administrations
 *
 * */
@Service
public class HistoryServiceImpl implements HistoryService {

    @Autowired
    private HistoryRepository historyRepository;
    @Autowired
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
