package UZSL.application.mapper;

import UZSL.application.dto.stats.created.StatsCreatedDTO;
import UZSL.application.dto.stats.dto.StatsDTO;
import UZSL.application.dto.stats.dto.StatsPlayersDTO;
import UZSL.domain.model.entity.clubs.clubsInfo.ClubsSquadEntity;
import UZSL.domain.model.entity.stats.StatsEntity;
import UZSL.domain.model.entity.stats.StatsPlayersEntity;
import UZSL.shared.exception.AppBadException;
import UZSL.domain.repository.clubs.clubsInfo.ClubsSquadRepository;
import UZSL.domain.repository.stats.StatsPlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Component
public class StatsMapper {

    @Autowired
    private ClubsSquadRepository clubsSquadRepository;
    @Autowired
    private StatsPlayerRepository statsPlayerRepository;

    /// ENTITY TO DTO CREATE
    public StatsEntity toEntity(StatsCreatedDTO createdDTO) {
        return StatsEntity.builder().
                statsName(createdDTO.getStatsName()).
                statsPlayersEntityList(toPlayersEntity(createdDTO)).
                build();
    }

    public List<StatsPlayersEntity> toPlayersEntity(StatsCreatedDTO createdDTO) {
        List<ClubsSquadEntity> allSquads = clubsSquadRepository.findAll();
        return createdDTO.getCreateStatsGoal().stream().map(playerDTO -> {
            String firstName = playerDTO.getFirstName();
            String lastName = playerDTO.getLastName();
            String clubNumber = playerDTO.getClubNumber();

            Optional<StatsPlayersEntity> foundPlayer = allSquads.stream()
                    .flatMap(squad -> Stream.of(
                            squad.getGoalKeepersEntityList().stream()
                                    .filter(p -> p.getFirstName().equalsIgnoreCase(firstName)
                                            || p.getLastName().equalsIgnoreCase(lastName)
                                            && p.getClubNumber().equals(clubNumber))
                                    .map(p -> buildStatsEntity(p.getClubsGoalKeepersId(), p.getFirstName(), p.getLastName(), p.getClubNumber(), squad.getClubsFullName())),

                            squad.getDefenderEntityList().stream()
                                    .filter(p -> p.getFirstName().equalsIgnoreCase(firstName)
                                            || p.getLastName().equalsIgnoreCase(lastName)
                                            && p.getClubNumber().equals(clubNumber))
                                    .map(p -> buildStatsEntity(p.getClubsDefendersId(), p.getFirstName(), p.getLastName(), p.getClubNumber(), squad.getClubsFullName())),

                            squad.getMidFieldersEntityList().stream()
                                    .filter(p -> p.getFirstName().equalsIgnoreCase(firstName)
                                            || p.getLastName().equalsIgnoreCase(lastName)
                                            && p.getClubNumber().equals(clubNumber))
                                    .map(p -> buildStatsEntity(p.getClubsMidFieldersId(), p.getFirstName(), p.getLastName(), p.getClubNumber(), squad.getClubsFullName())),

                            squad.getStrikersEntityList().stream()
                                    .filter(p -> p.getFirstName().equalsIgnoreCase(firstName)
                                            || p.getLastName().equalsIgnoreCase(lastName)
                                            && p.getClubNumber().equals(clubNumber))
                                    .map(p -> buildStatsEntity(p.getClubsStrikersId(), p.getFirstName(), p.getLastName(), p.getClubNumber(), squad.getClubsFullName()))
                    ).flatMap(s -> s)).findFirst();

            StatsPlayersEntity baseInfo = foundPlayer.orElseThrow(() -> new RuntimeException("Player not found for: " + firstName + " " + lastName + " #" + clubNumber));
            baseInfo.setTitle(playerDTO.getTitle());
            baseInfo.setGoals(playerDTO.getGoals());
            baseInfo.setAssist(playerDTO.getAssist());
            baseInfo.setShots(playerDTO.getShots());
            baseInfo.setOwnGoal(playerDTO.getOwnGoal());
            baseInfo.setPenalties(playerDTO.getPenalties());
            statsPlayerRepository.save(baseInfo);
            return baseInfo;
        }).collect(Collectors.toList());
    }

    private StatsPlayersEntity buildStatsEntity(String id, String firstName, String lastName, String clubNumber, String clubsFullName) {
        return StatsPlayersEntity.builder()
                .statsPlayerId(id)
                .firstName(firstName)
                .lastName(lastName)
                .clubNumber(clubNumber)
                .clubsFullName(clubsFullName)
                .build();
    }

    /// ENTITY TO DTO UPDATE
    public StatsEntity toUpdateEntity(String statesId, StatsCreatedDTO createdDTO) {
        return StatsEntity.builder().
                statsName(createdDTO.getStatsName()).
                statsPlayersEntityList(toUpdatePlayersEntity(statesId, createdDTO)).
                build();
    }

    public List<StatsPlayersEntity> toUpdatePlayersEntity(String statesId, StatsCreatedDTO createdDTO) {
        StatsPlayersEntity playersEntity = statsPlayerRepository.findById(statesId).orElseThrow(() -> new AppBadException("Players id is not found!"));
        return createdDTO.getCreateStatsGoal().stream().map(playerDTO -> {
            playersEntity.setTitle(playerDTO.getTitle());
            playersEntity.setFirstName(playerDTO.getFirstName());
            playersEntity.setLastName(playerDTO.getLastName());
            playersEntity.setClubNumber(playerDTO.getClubNumber());
            playersEntity.setGoals(playerDTO.getGoals());
            playersEntity.setAssist(playerDTO.getAssist());
            playersEntity.setShots(playerDTO.getShots());
            playersEntity.setOwnGoal(playerDTO.getOwnGoal());
            playersEntity.setPenalties(playerDTO.getPenalties());
            statsPlayerRepository.save(playersEntity);
            return playersEntity;
        }).collect(Collectors.toList());
    }

    /// DTO TO ENTITY GET
    public StatsDTO toStatsDTO(StatsEntity statsEntity) {
        return StatsDTO.builder()
                .statsId(statsEntity.getStatsId())
                .statsName(statsEntity.getStatsName())
                .statsPlayersList(playersDTOList(statsEntity))
                .build();
    }

    public List<StatsPlayersDTO> playersDTOList(StatsEntity statsEntity) {
        return statsEntity.getStatsPlayersEntityList().stream().map(playersEntity ->
                StatsPlayersDTO.builder()
                        .statsPlayerId(playersEntity.getStatsPlayerId())
                        .title(playersEntity.getTitle())
                        .firstName(playersEntity.getFirstName())
                        .lastName(playersEntity.getLastName())
                        .clubNumber(playersEntity.getClubNumber())
                        .clubsFullName(playersEntity.getClubsFullName())
                        .goals(playersEntity.getGoals())
                        .assist(playersEntity.getAssist())
                        .shots(playersEntity.getShots())
                        .ownGoal(playersEntity.getOwnGoal())
                        .penalties(playersEntity.getPenalties())
                        .createdAt(playersEntity.getCreatedAt())
                        .build()).collect(Collectors.toList());
    }


}
