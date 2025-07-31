package UZSL.application.service.stats;

import UZSL.application.dto.stats.created.StatsCreatedDTO;
import UZSL.application.dto.stats.dto.StatsDTO;
import UZSL.domain.model.entity.stats.StatsEntity;
import UZSL.application.mapper.StatsMapper;
import UZSL.domain.service.stats.StatsService;
import UZSL.domain.repository.stats.StatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatsServiceImpl implements StatsService {

    @Autowired
    private StatsRepository statsRepository;
    @Autowired
    private StatsMapper statsMapper;

    @Override
    public StatsDTO createStats(StatsCreatedDTO createdDTO) {
        StatsEntity statsEntity = statsMapper.toEntity(createdDTO);
        statsRepository.save(statsEntity);
        return statsMapper.toStatsDTO(statsEntity);
    }

    @Override
    public List<StatsDTO> getAllStats() {
        return statsRepository.findAll().stream().map(StatsEntity::getStatsId).sorted(Comparator.comparing(StatsDTO::setStatsId).reversed()).collect(Collectors.toList());
    }

    @Override
    public StatsDTO updateStats(String statesId, StatsCreatedDTO createdDTO) {
        StatsEntity statsEntity = statsMapper.toUpdateEntity(statesId, createdDTO);
        statsRepository.save(statsEntity);
        return statsMapper.toStatsDTO(statsEntity);
    }

    @Override
    public String deleteStats(String statesId) {
        statsRepository.deleteById(statesId);
        return "";
    }
}
