package UZSL.service.table;

import UZSL.dto.table.ClubsTableDTO;
import UZSL.entity.match.AwayTeamEntity;
import UZSL.entity.match.HomeTeamEntity;
import UZSL.entity.table.ClubsTableEntity;
import UZSL.exception.AppBadException;
import UZSL.repository.match.AwayTeamRepository;
import UZSL.repository.match.HomeTeamRepository;
import UZSL.repository.table.ClubsTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ClubsTableServiceImpl implements ClubsTableService {

    @Autowired
    private ClubsTableRepository clubsTableRepository;
    @Autowired
    private HomeTeamRepository homeTeamRepository;
    @Autowired
    private AwayTeamRepository awayTeamRepository;

    @Override
    public void calculateTeamStatsFromMatches(String homeTeamsId, String awayTeamsId) {
        // Klub nomi bo‘yicha tez topish uchun map tayyorlash
        Map<String, ClubsTableEntity> clubMap = new HashMap<>();
        for (ClubsTableEntity club : clubsTableRepository.findAll()) {
            if (club.getHomeClubName() != null) {
                clubMap.put(club.getHomeClubName().trim().toLowerCase(), club);
            }
            if (club.getVisitorClubName() != null) {
                clubMap.put(club.getVisitorClubName().trim().toLowerCase(), club);
            }
        }

        // Home va Away teamlarni olib kelish
        HomeTeamEntity homeTeamEntity = homeTeamRepository.findById(homeTeamsId)
                .orElseThrow(() -> new AppBadException("Home team id: " + homeTeamsId + " is not found!"));
        AwayTeamEntity awayTeamEntity = awayTeamRepository.findById(awayTeamsId)
                .orElseThrow(() -> new AppBadException("Away team id: " + awayTeamsId + " is not found!"));

        String homeName = homeTeamEntity.getHomeTeamName().trim().toLowerCase();
        String awayName = awayTeamEntity.getAwayTeamName().trim().toLowerCase();

        int homeGoals = homeTeamEntity.getOwnGoal();
        int awayGoals = awayTeamEntity.getAwayGoal();

        ClubsTableEntity homeTeam = clubMap.get(homeName);
        ClubsTableEntity awayTeam = clubMap.get(awayName);

        // Agar ClubsTableEntity topilmasa, hisoblab bo‘lmaydi
        if (homeTeam == null || awayTeam == null) return;

        // O'yinlar soni oshiriladi
        homeTeam.setPlayedGames(homeTeam.getPlayedGames() + 1);
        awayTeam.setPlayedGames(awayTeam.getPlayedGames() + 1);

        // Gollar hisoblanadi
        homeTeam.setGoalsOwn(homeTeam.getGoalsOwn() + homeGoals);
        homeTeam.setGoalsAgainst(homeTeam.getGoalsAgainst() + awayGoals);

        awayTeam.setGoalsOwn(awayTeam.getGoalsOwn() + awayGoals);
        awayTeam.setGoalsAgainst(awayTeam.getGoalsAgainst() + homeGoals);

        // Yutuq/Durrang/Yutqazish holatini aniqlash
        if (homeGoals > awayGoals) {
            homeTeam.setWon(homeTeam.getWon() + 1);
            awayTeam.setLost(awayTeam.getLost() + 1);
        } else if (awayGoals > homeGoals) {
            awayTeam.setWon(awayTeam.getWon() + 1);
            homeTeam.setLost(homeTeam.getLost() + 1);
        } else {
            homeTeam.setDrawn(homeTeam.getDrawn() + 1);
            awayTeam.setDrawn(awayTeam.getDrawn() + 1);
        }

        // Ochkolar yangilanadi
        homeTeam.setTotalPoints(homeTeam.getWon() * 3 + homeTeam.getDrawn());
        awayTeam.setTotalPoints(awayTeam.getWon() * 3 + awayTeam.getDrawn());

        // Saqlash
        clubsTableRepository.saveAll(Arrays.asList(homeTeam, awayTeam));
    }

    @Override
    public List<ClubsTableDTO> getFullClubsTable() {
        List<ClubsTableEntity> entityList = clubsTableRepository.findAll();
        return entityList.stream().map(ClubsTableDTO::toDTO).collect(Collectors.toList());
    }


}
