package UZSL.domain.service.clubs.info.player;

import UZSL.application.dto.clubs.clubs_info.player.created.PlayerCreatedDTO;
import UZSL.application.dto.clubs.clubs_info.player.dto.PlayerDTO;

import java.util.List;

public interface PlayerService {

    PlayerDTO createPlayerInfo(String playerId, PlayerCreatedDTO playerCreated);

    List<PlayerDTO> getAllPlayerInfo(String playerId);

    PlayerDTO updatePlayerInfo(String playerId, PlayerCreatedDTO playerCreated);

    String deletePlayerInfo(String playerId);
}
