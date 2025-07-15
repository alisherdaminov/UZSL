package UZSL.service.table;

import UZSL.dto.table.ClubsTableDTO;
import UZSL.entity.match.MatchEntity;
import UZSL.entity.match.TeamsEntity;
import UZSL.entity.table.ClubsTableEntity;
import UZSL.repository.match.MatchRepository;
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
    private MatchRepository matchRepository;

    public void calculateTeamStatsFromMatches() {
        List<MatchEntity> matches = matchRepository.findAll();
        Map<String, ClubsTableEntity> teamMap = new HashMap<>();

        for (MatchEntity match : matches) {
            for (TeamsEntity teamsEntity : match.getTeamsEntityList()) {
                String home = teamsEntity.getHomeTeamEntity().getHomeTeamName();
                String away = teamsEntity.getAwayTeamEntity().getAwayTeamName();
                int homeGoals = teamsEntity.getHomeTeamEntity().getOwnGoal();
                int awayGoals = teamsEntity.getAwayTeamEntity().getAwayGoal();

                teamMap.putIfAbsent(home, new ClubsTableEntity());
                teamMap.putIfAbsent(away, new ClubsTableEntity());

                ClubsTableEntity homeTeam = teamMap.get(home);
                ClubsTableEntity awayTeam = teamMap.get(away);

                homeTeam.setHomeClubName(home);
                awayTeam.setVisitorClubName(away);

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
                teamMap.put(home, homeTeam);
                teamMap.put(away, awayTeam);
            }
        }
        clubsTableRepository.saveAll(teamMap.values());
    }


    public List<ClubsTableDTO> getFullClubsTable() {
        List<ClubsTableEntity> entityList = clubsTableRepository.findAll();
        return entityList.stream().map(ClubsTableDTO::toDTO).collect(Collectors.toList());
    }


}
