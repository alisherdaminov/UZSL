package UZSL.service.clubs.info.player;

import UZSL.dto.clubs.clubs_info.player.created.PlayerCreatedDTO;
import UZSL.dto.clubs.clubs_info.player.dto.PlayerDTO;
import UZSL.dto.extensions.PlayerServiceDTO;
import UZSL.entity.clubs.clubsInfo.*;
import UZSL.entity.clubs.clubsInfo.player_info.PlayerCareerEntity;
import UZSL.entity.clubs.clubsInfo.player_info.PlayerDetailEntity;
import UZSL.entity.clubs.clubsInfo.player_info.PlayerEntity;
import UZSL.exception.AppBadException;
import UZSL.repository.clubs.clubsInfo.*;
import UZSL.repository.clubs.clubsInfo.player.PlayerCareerRepository;
import UZSL.repository.clubs.clubsInfo.player.PlayerDetailRepository;
import UZSL.repository.clubs.clubsInfo.player.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private GoalKeepersRepository goalKeepersRepository;
    @Autowired
    private DefendersRepository defendersRepository;
    @Autowired
    private MidfieldersRepository midfieldersRepository;
    @Autowired
    private StrikersRepository strikersRepository;
    @Autowired
    private PlayerDetailRepository playerDetailRepository;
    @Autowired
    private PlayerCareerRepository playerCareerRepository;
    @Autowired
    private PlayerServiceDTO playerServiceDTO;

    /// CREATE PLAYER INFO
    @Override
    public PlayerDTO createPlayerInfo(String playerId, PlayerCreatedDTO playerCreated) {
        Optional<GoalKeepersEntity> optionalGoalKeepers = goalKeepersRepository.findById(playerId);
        Optional<DefendersEntity> optionalDefenders = defendersRepository.findById(playerId);
        Optional<MidFieldersEntity> optionalMidFielders = midfieldersRepository.findById(playerId);
        Optional<StrikersEntity> optionalStrikers = strikersRepository.findById(playerId);

        if (optionalGoalKeepers.isEmpty() && optionalDefenders.isEmpty()
                && optionalMidFielders.isEmpty() && optionalStrikers.isEmpty()) {
            throw new AppBadException("This id: " + playerId + " is not found!");
        }

        PlayerDetailEntity playerDetail = new PlayerDetailEntity();
        playerDetail.setFouls(playerCreated.getPlayerDetailCreated().getFouls());
        playerDetail.setYellowCards(playerCreated.getPlayerDetailCreated().getYellowCards());
        playerDetail.setAppearances(playerCreated.getPlayerDetailCreated().getAppearances());
        playerDetail.setSprints(playerCreated.getPlayerDetailCreated().getSprints());
        playerDetail.setIntensiveRuns(playerCreated.getPlayerDetailCreated().getIntensiveRuns());
        playerDetail.setDistanceKm(playerCreated.getPlayerDetailCreated().getDistanceKm());
        playerDetail.setSpeedKmH(playerCreated.getPlayerDetailCreated().getSpeedKmH());
        playerDetail.setCrosses(playerCreated.getPlayerDetailCreated().getCrosses());

        //calculating which is the same id for selected players id in db
        if (optionalGoalKeepers.isPresent()) {
            playerDetail.setGoalKeepersPlayer(optionalGoalKeepers.get());
        } else if (optionalDefenders.isPresent()) {
            playerDetail.setDefendersPlayer(optionalDefenders.get());
        } else if (optionalMidFielders.isPresent()) {
            playerDetail.setMidFieldersPlayer(optionalMidFielders.get());
        } else optionalStrikers.ifPresent(playerDetail::setStrikersPlayer);
        playerDetailRepository.save(playerDetail);

        PlayerCareerEntity playerCareer = new PlayerCareerEntity();
        playerCareer.setAppearances(playerCreated.getPlayerCareerCreated().getAppearances());
        playerCareer.setGoals(playerCreated.getPlayerCareerCreated().getGoals());
        playerCareer.setAssists(playerCreated.getPlayerCareerCreated().getAssists());
        playerCareer.setBallActions(playerCreated.getPlayerCareerCreated().getBallActions());
        playerCareer.setDistanceKmForSeason(playerCreated.getPlayerCareerCreated().getDistanceKmForSeason());
        playerCareer.setPenalties(playerCreated.getPlayerCareerCreated().getPenalties());


        //calculating which is the same id for selected players id in db
        if (optionalGoalKeepers.isPresent()) {
            playerCareer.setGoalKeepersCareer(optionalGoalKeepers.get());
        } else if (optionalDefenders.isPresent()) {
            playerCareer.setDefendersCareer(optionalDefenders.get());
        } else if (optionalMidFielders.isPresent()) {
            playerCareer.setMidFieldersCareer(optionalMidFielders.get());
        } else optionalStrikers.ifPresent(playerCareer::setStrikersCareer);
        playerCareerRepository.save(playerCareer);
        // Parent entity
        PlayerEntity playerEntity = new PlayerEntity();
        playerEntity.setPlayerDetailEntity(playerDetail);
        playerEntity.setPlayerCareerEntity(playerCareer);
        playerRepository.save(playerEntity);
        return playerServiceDTO.toPlayerDTO(playerEntity);
    }

    /// GET ALL PLAYER INFO LIST
    @Override
    public List<PlayerDTO> getAllPlayerInfo(String playerId) {
        List<PlayerEntity> players = playerRepository.findAll();
        return players.stream()
                .filter(player -> {
                    PlayerDetailEntity detail = player.getPlayerDetailEntity();
                    PlayerCareerEntity career = player.getPlayerCareerEntity();

                    boolean isDetailMatch = (detail.getGoalKeepersPlayer() != null && detail.getGoalKeepersPlayer().getClubsGoalKeepersId().equals(playerId)) ||
                            (detail.getDefendersPlayer() != null && detail.getDefendersPlayer().getClubsDefendersId().equals(playerId)) ||
                            (detail.getMidFieldersPlayer() != null && detail.getMidFieldersPlayer().getClubsMidFieldersId().equals(playerId)) ||
                            (detail.getStrikersPlayer() != null && detail.getStrikersPlayer().getClubsStrikersId().equals(playerId));

                    boolean isCareerMatch = (career.getGoalKeepersCareer() != null && career.getGoalKeepersCareer().getClubsGoalKeepersId().equals(playerId)) ||
                            (career.getDefendersCareer() != null && career.getDefendersCareer().getClubsDefendersId().equals(playerId)) ||
                            (career.getMidFieldersCareer() != null && career.getMidFieldersCareer().getClubsMidFieldersId().equals(playerId)) ||
                            (career.getStrikersCareer() != null && career.getStrikersCareer().getClubsStrikersId().equals(playerId));
                    return isDetailMatch && isCareerMatch;
                })
                .map(playerServiceDTO::toPlayerDTO)
                .collect(Collectors.toList());
    }

    /// UPDATE PLAYER INFO
    @Override
    public PlayerDTO updatePlayerInfo(String playerId, PlayerCreatedDTO playerCreated) {
        Optional<GoalKeepersEntity> optionalGoalKeepers = goalKeepersRepository.findById(playerId);
        Optional<DefendersEntity> optionalDefenders = defendersRepository.findById(playerId);
        Optional<MidFieldersEntity> optionalMidFielders = midfieldersRepository.findById(playerId);
        Optional<StrikersEntity> optionalStrikers = strikersRepository.findById(playerId);
        Optional<PlayerDetailEntity> optionalPlayerDetail = playerDetailRepository.findById(playerId);
        Optional<PlayerCareerEntity> optionalPlayerCareer = playerCareerRepository.findById(playerId);

        if (optionalGoalKeepers.isEmpty() && optionalDefenders.isEmpty() && optionalMidFielders.isEmpty() && optionalStrikers.isEmpty()) {
            throw new AppBadException("This player detail's id: " + playerId + " is not found!");
        }
        if (optionalPlayerDetail.isEmpty() && optionalPlayerCareer.isEmpty()) {
            throw new AppBadException("This id: " + optionalPlayerDetail.get().getPlayerDetailId() + " is not found!");
        }
        PlayerDetailEntity playerDetail = optionalPlayerDetail.get();
        playerDetail.setFouls(playerCreated.getPlayerDetailCreated().getFouls());
        playerDetail.setYellowCards(playerCreated.getPlayerDetailCreated().getYellowCards());
        playerDetail.setAppearances(playerCreated.getPlayerDetailCreated().getAppearances());
        playerDetail.setSprints(playerCreated.getPlayerDetailCreated().getSprints());
        playerDetail.setIntensiveRuns(playerCreated.getPlayerDetailCreated().getIntensiveRuns());
        playerDetail.setDistanceKm(playerCreated.getPlayerDetailCreated().getDistanceKm());
        playerDetail.setSpeedKmH(playerCreated.getPlayerDetailCreated().getSpeedKmH());
        playerDetail.setCrosses(playerCreated.getPlayerDetailCreated().getCrosses());

        //calculating which is the same id for selected players id in db
        if (optionalGoalKeepers.isPresent()) {
            playerDetail.setGoalKeepersPlayer(optionalGoalKeepers.get());
        } else if (optionalDefenders.isPresent()) {
            playerDetail.setDefendersPlayer(optionalDefenders.get());
        } else if (optionalMidFielders.isPresent()) {
            playerDetail.setMidFieldersPlayer(optionalMidFielders.get());
        } else optionalStrikers.ifPresent(playerDetail::setStrikersPlayer);
        playerDetailRepository.save(playerDetail);

        if (optionalPlayerCareer.isEmpty()) {
            throw new AppBadException("This player career's id : " + optionalPlayerCareer.get().getPlayerDetailId() + " is not found!");
        }
        PlayerCareerEntity playerCareer = optionalPlayerCareer.get();
        playerCareer.setAppearances(playerCreated.getPlayerCareerCreated().getAppearances());
        playerCareer.setGoals(playerCreated.getPlayerCareerCreated().getGoals());
        playerCareer.setAssists(playerCreated.getPlayerCareerCreated().getAssists());
        playerCareer.setBallActions(playerCreated.getPlayerCareerCreated().getBallActions());
        playerCareer.setDistanceKmForSeason(playerCreated.getPlayerCareerCreated().getDistanceKmForSeason());
        playerCareer.setPenalties(playerCreated.getPlayerCareerCreated().getPenalties());

        //calculating which is the same id for selected players id in db
        if (optionalGoalKeepers.isPresent()) {
            playerCareer.setGoalKeepersCareer(optionalGoalKeepers.get());
        } else if (optionalDefenders.isPresent()) {
            playerCareer.setDefendersCareer(optionalDefenders.get());
        } else if (optionalMidFielders.isPresent()) {
            playerCareer.setMidFieldersCareer(optionalMidFielders.get());
        } else optionalStrikers.ifPresent(playerCareer::setStrikersCareer);
        playerCareerRepository.save(playerCareer);
        // Parent entity
        PlayerEntity playerEntity = new PlayerEntity();
        playerEntity.setPlayerDetailEntity(playerDetail);
        playerEntity.setPlayerCareerEntity(playerCareer);
        playerRepository.save(playerEntity);
        return playerServiceDTO.toPlayerDTO(playerEntity);
    }

    /// DELETE PLAYER INFO
    @Override
    public String deletePlayerInfo(String playerInfoId) {
        playerRepository.deleteById(playerInfoId);
        return "";
    }
}