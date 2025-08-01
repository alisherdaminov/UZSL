package UZSL.application.service.clubs.info.player;

import UZSL.application.dto.clubs.clubs_info.player.created.PlayerCreatedDTO;
import UZSL.application.dto.clubs.clubs_info.player.dto.PlayerDTO;
import UZSL.domain.model.entity.clubs.clubsInfo.DefendersEntity;
import UZSL.domain.model.entity.clubs.clubsInfo.GoalKeepersEntity;
import UZSL.domain.model.entity.clubs.clubsInfo.MidFieldersEntity;
import UZSL.domain.model.entity.clubs.clubsInfo.StrikersEntity;
import UZSL.domain.service.clubs.info.player.PlayerService;
import UZSL.domain.repository.clubs.clubsInfo.DefendersRepository;
import UZSL.domain.repository.clubs.clubsInfo.GoalKeepersRepository;
import UZSL.domain.repository.clubs.clubsInfo.MidfieldersRepository;
import UZSL.domain.repository.clubs.clubsInfo.StrikersRepository;
import UZSL.application.mapper.PlayerMapper;
import UZSL.domain.model.entity.clubs.clubsInfo.player_info.PlayerCareerEntity;
import UZSL.domain.model.entity.clubs.clubsInfo.player_info.PlayerDetailEntity;
import UZSL.domain.model.entity.clubs.clubsInfo.player_info.PlayerEntity;
import UZSL.shared.exception.AppBadException;
import UZSL.domain.repository.clubs.clubsInfo.player.PlayerCareerRepository;
import UZSL.domain.repository.clubs.clubsInfo.player.PlayerDetailRepository;
import UZSL.domain.repository.clubs.clubsInfo.player.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * PlayerServiceImpl implements PlayerService which has own functions override and usages for create, get, update, delete after saved goal keeper, defenders,
 * midfielders,strikers, once fetching of goal keeper, defenders, midfielders,strikers that depend to players detail and career and then it will be done!
 * PlayerMapper is for mapper.
 * Before creation of the data that players detail and career for shown just player's id!
 * */
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
    private PlayerMapper playerMapper;

    /// CREATE PLAYER INFO
    @Override
    public PlayerDTO createPlayerInfo(String playerId, PlayerCreatedDTO playerCreated) {
        Optional<GoalKeepersEntity> optionalGoalKeepers = goalKeepersRepository.findById(playerId);
        Optional<DefendersEntity> optionalDefenders = defendersRepository.findById(playerId);
        Optional<MidFieldersEntity> optionalMidFielders = midfieldersRepository.findById(playerId);
        Optional<StrikersEntity> optionalStrikers = strikersRepository.findById(playerId);

        if (optionalGoalKeepers.isEmpty() && optionalDefenders.isEmpty() && optionalMidFielders.isEmpty() && optionalStrikers.isEmpty()) {
            throw new AppBadException("This id: " + playerId + " is not found!");
        }

        PlayerDetailEntity playerDetail = PlayerMapper.toPlayerDetailEntity(playerCreated);
        if (optionalGoalKeepers.isPresent()) {
            playerDetail.setGoalKeepersPlayer(optionalGoalKeepers.get());
        } else if (optionalDefenders.isPresent()) {
            playerDetail.setDefendersPlayer(optionalDefenders.get());
        } else if (optionalMidFielders.isPresent()) {
            playerDetail.setMidFieldersPlayer(optionalMidFielders.get());
        } else optionalStrikers.ifPresent(playerDetail::setStrikersPlayer);
        playerDetailRepository.save(playerDetail);

        PlayerCareerEntity playerCareer = PlayerMapper.toPlayerCareerEntity(playerCreated);
        if (optionalGoalKeepers.isPresent()) {
            playerCareer.setGoalKeepersCareer(optionalGoalKeepers.get());
        } else if (optionalDefenders.isPresent()) {
            playerCareer.setDefendersCareer(optionalDefenders.get());
        } else if (optionalMidFielders.isPresent()) {
            playerCareer.setMidFieldersCareer(optionalMidFielders.get());
        } else optionalStrikers.ifPresent(playerCareer::setStrikersCareer);
        playerCareerRepository.save(playerCareer);

        // Parent entity
        PlayerEntity playerEntity = PlayerEntity.builder()
                .playerDetailEntity(playerDetail)
                .playerCareerEntity(playerCareer)
                .build();
        playerRepository.save(playerEntity);
        return PlayerMapper.toDTO(playerEntity);
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
                .map(PlayerMapper::toDTO)
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
        PlayerDetailEntity playerDetail = PlayerMapper.toPlayerDetailEntity(playerCreated);
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
        PlayerCareerEntity playerCareer = PlayerMapper.toPlayerCareerEntity(playerCreated);
        if (optionalGoalKeepers.isPresent()) {
            playerCareer.setGoalKeepersCareer(optionalGoalKeepers.get());
        } else if (optionalDefenders.isPresent()) {
            playerCareer.setDefendersCareer(optionalDefenders.get());
        } else if (optionalMidFielders.isPresent()) {
            playerCareer.setMidFieldersCareer(optionalMidFielders.get());
        } else optionalStrikers.ifPresent(playerCareer::setStrikersCareer);
        playerCareerRepository.save(playerCareer);

        // Parent entity
        PlayerEntity playerEntity = PlayerEntity.builder()
                .playerDetailEntity(playerDetail)
                .playerCareerEntity(playerCareer)
                .build();
        playerRepository.save(playerEntity);
        return PlayerMapper.toDTO(playerEntity);
    }

    /// DELETE PLAYER INFO
    @Override
    public String deletePlayerInfo(String playerInfoId) {
        playerRepository.deleteById(playerInfoId);
        return "";
    }
}