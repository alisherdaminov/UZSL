package UZSL.service.clubs.info;

import UZSL.dto.app.AppResponse;
import UZSL.dto.clubs.clubs_info.created.ClubsSquadCreatedDTO;
import UZSL.dto.clubs.clubs_info.dto.ClubsSquadDTO;

import java.util.List;

public interface ClubsInfoService {


    ClubsSquadDTO createSquadAndProfile(Integer userId, String clubsId, ClubsSquadCreatedDTO squadCreatedDTO);

    List<ClubsSquadDTO> getClubsInfoList();

    ClubsSquadDTO updateSquad(String clubsId, ClubsSquadCreatedDTO squadCreatedDTO);

    String deleteSquad(String clubsId);

}
