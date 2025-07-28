package UZSL.service.clubs.table;

import UZSL.mapper.ClubsTableMapper;
import UZSL.dto.clubs.match_info.ClubsTableDTO;
import UZSL.entity.match.AwayTeamEntity;
import UZSL.entity.match.HomeTeamEntity;
import UZSL.entity.clubs.table.ClubsTableAwayEntity;
import UZSL.entity.clubs.table.ClubsTableEntity;
import UZSL.entity.clubs.table.ClubsTableHomeEntity;
import UZSL.exception.AppBadException;
import UZSL.repository.match.AwayTeamRepository;
import UZSL.repository.match.HomeTeamRepository;
import UZSL.repository.clubs.table.ClubsTableAwayRepository;
import UZSL.repository.clubs.table.ClubsTableHomeRepository;
import UZSL.repository.clubs.table.ClubsTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ClubsTableServiceImpl implements ClubsTableService {

    @Autowired
    private ClubsTableRepository clubsTableRepository;
    @Autowired
    private HomeTeamRepository homeTeamRepository;
    @Autowired
    private AwayTeamRepository awayTeamRepository;
    private ClubsTableMapper clubsTableMapper;
    @Autowired
    private ClubsTableAwayRepository clubsTableAwayRepository;
    @Autowired
    private ClubsTableHomeRepository clubsTableHomeRepository;

    /// CREATE CLUBS TABLE AND CALCULATE WITH SAVING IN ClubsTableRepository DATABASE
    @Override
    public void calculateTeamStatsFromMatches(String homeTeamsId, String awayTeamsId) {
        HomeTeamEntity homeTeamEntity = homeTeamRepository.findById(homeTeamsId).orElseThrow(() -> new AppBadException("Home team id: " + homeTeamsId + " is not found!"));
        AwayTeamEntity awayTeamEntity = awayTeamRepository.findById(awayTeamsId).orElseThrow(() -> new AppBadException("Away team id: " + awayTeamsId + " is not found!"));

        String homeName = homeTeamEntity.getHomeTeamName();
        String awayName = awayTeamEntity.getAwayTeamName();

        // find ClubsTableEntity that already contains this pair (home + away)
        List<ClubsTableEntity> optionalTable = clubsTableRepository.findAllByHomeClubNameAndVisitorClubName(homeName, awayName);
        // Clubs Entity is created new obj and getting saved data from HomeTeamEntity
        ClubsTableHomeEntity clubsTableHome;
        ClubsTableAwayEntity clubsTableAway;
        ClubsTableEntity clubsTableEntity;

        if (!optionalTable.isEmpty()) {
            // Mavjud bo‘lsa, shuni yangilaymiz
            clubsTableEntity = optionalTable.get(0);
            clubsTableHome = clubsTableEntity.getClubsTableHomeEntity();
            clubsTableAway = clubsTableEntity.getClubsTableAwayEntity();
        } else {
            // Yangi obyektlar
            clubsTableHome = new ClubsTableHomeEntity();
            clubsTableAway = new ClubsTableAwayEntity();
            clubsTableEntity = new ClubsTableEntity();
            clubsTableEntity.setCreatedAt(LocalDateTime.now());
        }

        // club name’lar
        clubsTableHome.setHomeClubName(homeName);
        clubsTableAway.setAwayClubName(awayName);

        int homeGoals = homeTeamEntity.getOwnGoal();
        int awayGoals = awayTeamEntity.getAwayGoal();

        // Played games
        clubsTableHome.setPlayedGames(safeAdd(clubsTableHome.getPlayedGames(), 1));
        clubsTableAway.setPlayedGames(safeAdd(clubsTableAway.getPlayedGames(), 1));

        clubsTableHome.setGoalsOwn(safeAdd(clubsTableHome.getGoalsOwn(), homeGoals));
        clubsTableHome.setGoalsAgainst(safeAdd(clubsTableHome.getGoalsAgainst(), awayGoals));

        clubsTableAway.setGoalsOwn(safeAdd(clubsTableAway.getGoalsOwn(), awayGoals));
        clubsTableAway.setGoalsAgainst(safeAdd(clubsTableAway.getGoalsAgainst(), homeGoals));

        if (homeGoals > awayGoals) {
            clubsTableHome.setWon(safeAdd(clubsTableHome.getWon(), 1));
            clubsTableAway.setLost(safeAdd(clubsTableAway.getLost(), 1));
        } else if (awayGoals > homeGoals) {
            clubsTableAway.setWon(safeAdd(clubsTableAway.getWon(), 1));
            clubsTableHome.setLost(safeAdd(clubsTableHome.getLost(), 1));
        } else {
            clubsTableHome.setDrawn(safeAdd(clubsTableHome.getDrawn(), 1));
            clubsTableAway.setDrawn(safeAdd(clubsTableAway.getDrawn(), 1));
        }
        // Points
        clubsTableHome.setTotalPoints(clubsTableHome.getWon() * 3 + clubsTableHome.getDrawn());
        clubsTableAway.setTotalPoints(clubsTableAway.getWon() * 3 + clubsTableAway.getDrawn());

        // Save
        clubsTableHome = clubsTableHomeRepository.save(clubsTableHome);
        clubsTableAway = clubsTableAwayRepository.save(clubsTableAway);

        clubsTableEntity.setClubsTableHomeEntity(clubsTableHome);
        clubsTableEntity.setClubsTableAwayEntity(clubsTableAway);
        clubsTableRepository.save(clubsTableEntity);
    }

    /// safeAdd function is for safe additions
    private int safeAdd(Integer base, int valueToAdd) {
        return (base == null ? 0 : base) + valueToAdd;
    }

    /// GET ALL CLUBS TABLE RESULTS LIST
    @Override
    public List<ClubsTableDTO> getFullClubsTable() {
        List<ClubsTableEntity> entityList = clubsTableRepository.getLeagueTable();
        return entityList.stream().map(clubsTableMapper::toClubsTableDTO).collect(Collectors.toList());
    }


}
