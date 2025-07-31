package UZSL.domain.service.clubs.info;

import UZSL.application.dto.clubs.clubs_info.created.ClubsSquadCreatedDTO;
import UZSL.application.dto.clubs.clubs_info.dto.ClubsSquadDTO;

import java.util.List;

public interface ClubsInfoService {


    ClubsSquadDTO createSquadAndProfile( String clubsId, ClubsSquadCreatedDTO squadCreatedDTO);

    List<ClubsSquadDTO> getClubsInfoList();

    ClubsSquadDTO updateSquad(String clubsId,  String playerId,ClubsSquadCreatedDTO squadCreatedDTO);

    String deleteSquad(String clubsId);

}
