package UZSL.application.service.stats;

import UZSL.application.dto.stats.created.StatsCreatedDTO;
import UZSL.application.dto.stats.dto.StatsDTO;
import UZSL.application.dto.stats.dto.StatsPlayersDTO;
import UZSL.domain.model.entity.stats.StatsEntity;
import UZSL.application.mapper.StatsMapper;
import UZSL.domain.model.entity.stats.StatsPlayersEntity;
import UZSL.domain.repository.stats.StatsPlayerRepository;
import UZSL.domain.service.stats.StatsService;
import UZSL.domain.repository.stats.StatsRepository;
import UZSL.shared.exception.AppBadException;
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
    private StatsPlayerRepository statsPlayerRepository;
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
        return statsRepository.findAll().stream()
                .map(statsMapper::toStatsDTO)
                .sorted(Comparator.comparing(StatsDTO::getStatsId).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public StatsDTO getStatsById(String playerId) {
        StatsPlayersEntity player = statsPlayerRepository.findById(playerId).orElseThrow(() -> new AppBadException("Player ID is not found!"));
        String statsPlayerId = player.getStatsPlayerId();
        List<StatsPlayersEntity> topGoals = statsPlayerRepository.findByIdAndSortGoalsTop(statsPlayerId);
        List<StatsPlayersEntity> topAssists = statsPlayerRepository.findByIdAndSortAssistTop(statsPlayerId);
        List<StatsPlayersEntity> topShots = statsPlayerRepository.findByIdAndSortShotsTop(statsPlayerId);
        List<StatsPlayersEntity> topOwnGoals = statsPlayerRepository.findByIdAndSortOwnGoalsTop(statsPlayerId);
        List<StatsPlayersEntity> topPenalties = statsPlayerRepository.findByIdAndSortPenalties(statsPlayerId);
        return StatsDTO.builder()
                .statsId(statsPlayerId)
                .topGoals(topGoals.stream().map(statsMapper::toPlayersDTO).collect(Collectors.toList()))
                .topAssists(topAssists.stream().map(statsMapper::toPlayersDTO).collect(Collectors.toList()))
                .topShots(topShots.stream().map(statsMapper::toPlayersDTO).collect(Collectors.toList()))
                .topOwnGoals(topOwnGoals.stream().map(statsMapper::toPlayersDTO).collect(Collectors.toList()))
                .topPenalties(topPenalties.stream().map(statsMapper::toPlayersDTO).collect(Collectors.toList()))
                .build();
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
