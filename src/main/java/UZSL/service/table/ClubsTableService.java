package UZSL.service.table;

import UZSL.dto.table.ClubsTableDTO;
import UZSL.entity.match.MatchEntity;
import UZSL.entity.match.TeamsEntity;
import UZSL.entity.table.ClubsTableEntity;
import UZSL.repository.match.MatchRepository;
import UZSL.repository.match.TeamsRepository;
import UZSL.repository.table.ClubsTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ClubsTableService {

    @Autowired
    private ClubsTableRepository clubsTableRepository;
    @Autowired
    private TeamsRepository teamsRepository;

    public void calculateTeamStatsFromMatches() {
        List<TeamsEntity> teamsEntityList = teamsRepository.findAll();
        Map<String, ClubsTableEntity> teamMap = new HashMap<>();

        for (TeamsEntity teamsEntity : teamsEntityList) {
            String homeName = teamsEntity.getHomeTeamEntity().getHomeTeamName().trim().toLowerCase();
            String awayName = teamsEntity.getAwayTeamEntity().getAwayTeamName().trim().toLowerCase();

            int homeGoals = teamsEntity.getHomeTeamEntity().getOwnGoal();
            int awayGoals = teamsEntity.getAwayTeamEntity().getAwayGoal();

            // Get or create home team stats
            ClubsTableEntity homeTeam = teamMap.computeIfAbsent(homeName, k -> {
                ClubsTableEntity club = new ClubsTableEntity();
                club.setHomeClubName(homeName);
                return club;
            });

            // Get or create away team stats
            ClubsTableEntity awayTeam = teamMap.computeIfAbsent(awayName, k -> {
                ClubsTableEntity club = new ClubsTableEntity();
                club.setVisitorClubName(awayName);
                return club;
            });

            // Update stats
            homeTeam.setPlayedGames(homeTeam.getPlayedGames() + 1);
            awayTeam.setPlayedGames(awayTeam.getPlayedGames() + 1);

            homeTeam.setGoalsOwn(homeTeam.getGoalsOwn() + homeGoals);
            homeTeam.setGoalsAgainst(homeTeam.getGoalsAgainst() + awayGoals);

            awayTeam.setGoalsOwn(awayTeam.getGoalsOwn() + awayGoals);
            awayTeam.setGoalsAgainst(awayTeam.getGoalsAgainst() + homeGoals);

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

            homeTeam.setTotalPoints(homeTeam.getWon() * 3 + homeTeam.getDrawn());
            awayTeam.setTotalPoints(awayTeam.getWon() * 3 + awayTeam.getDrawn());
        }

        clubsTableRepository.saveAll(teamMap.values());
    }


    public List<ClubsTableDTO> getFullClubsTable() {
        List<ClubsTableEntity> entityList = clubsTableRepository.findAll();
        return entityList.stream().map(ClubsTableDTO::toDTO).collect(Collectors.toList());
    }


}
