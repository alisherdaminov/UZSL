package UZSL.dto.extensions;

import UZSL.dto.clubs.clubs_info.created.*;
import UZSL.dto.clubs.clubs_info.dto.*;
import UZSL.entity.clubs.clubsInfo.*;
import UZSL.repository.clubs.clubsInfo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ClubsSquadServiceDTO {

    @Autowired
    private GoalKeepersRepository goalKeepersRepository;
    @Autowired
    private DefendersRepository defendersRepository;
    @Autowired
    private MidfieldersRepository midfieldersRepository;
    @Autowired
    private StrikersRepository strikersRepository;
    @Autowired
    private ClubsProfileRepository clubsProfileRepository;

    //ClubsSquadDTO striker
    public StrikersEntity toStrikersEntity(StrikersCreatedDTO strikers, ClubsSquadEntity clubsSquad) {
        StrikersEntity strikersEntity = new StrikersEntity();
        strikersEntity.setFirstName(strikers.getFirstName());
        strikersEntity.setLastName(strikers.getLastName());
        strikersEntity.setClubNumber(strikers.getClubNumber());
        strikersEntity.setCreatedAt(LocalDateTime.now());
        strikersEntity.setClubsSquadInStriker(clubsSquad); // PARENT LINK
        strikersRepository.save(strikersEntity);
        return strikersEntity;
    }

    //ClubsSquadDTO midfielder
    public MidFieldersEntity toMidfieldersEntity(MidFieldersCreatedDTO midFielders, ClubsSquadEntity clubsSquad) {
        MidFieldersEntity midFieldersEntity = new MidFieldersEntity();
        midFieldersEntity.setFirstName(midFielders.getFirstName());
        midFieldersEntity.setLastName(midFielders.getLastName());
        midFieldersEntity.setClubNumber(midFielders.getClubNumber());
        midFieldersEntity.setCreatedAt(LocalDateTime.now());
        midFieldersEntity.setClubsSquadInMidfielder(clubsSquad); // PARENT LINK
        midfieldersRepository.save(midFieldersEntity);
        return midFieldersEntity;
    }

    //ClubsSquadDTO defender
    public DefendersEntity toDefendersEntity(DefendersCreatedDTO defenders, ClubsSquadEntity clubsSquad) {
        DefendersEntity defendersEntity = new DefendersEntity();
        defendersEntity.setFirstName(defenders.getFirstName());
        defendersEntity.setLastName(defenders.getLastName());
        defendersEntity.setClubNumber(defenders.getClubNumber());
        defendersEntity.setCreatedAt(LocalDateTime.now());
        defendersEntity.setClubsSquadInDefendersEntity(clubsSquad); // PARENT LINK
        defendersRepository.save(defendersEntity);
        return defendersEntity;
    }

    //ClubsSquadDTO goal keeper
    public GoalKeepersEntity toGoalKeeperEntity(GoalKeepersCreatedDTO goalKeepersDTO, ClubsSquadEntity clubsSquad) {
        GoalKeepersEntity goalKeepers = new GoalKeepersEntity();
        goalKeepers.setFirstName(goalKeepersDTO.getFirstName());
        goalKeepers.setLastName(goalKeepersDTO.getLastName());
        goalKeepers.setClubNumber(goalKeepersDTO.getClubNumber());
        goalKeepers.setCreatedAt(LocalDateTime.now());
        goalKeepers.setClubsSquadInGoalKeepers(clubsSquad); // PARENT LINK
        goalKeepersRepository.save(goalKeepers);
        return goalKeepers;
    }

    //ClubsSquadDTO profile
    public ClubsProfileEntity getClubsProfileEntity(ClubsSquadCreatedDTO squadCreatedDTO, ClubsSquadEntity saved) {
        ClubsProfileEntity clubsProfile = new ClubsProfileEntity();
        clubsProfile.setStadiumName(squadCreatedDTO.getClubsProfile().getStadiumName());
        clubsProfile.setFounded(squadCreatedDTO.getClubsProfile().getFounded());
        clubsProfile.setClubsColor(squadCreatedDTO.getClubsProfile().getClubsColor());
        clubsProfile.setStreet(squadCreatedDTO.getClubsProfile().getStreet());
        clubsProfile.setCity(squadCreatedDTO.getClubsProfile().getCity());
        clubsProfile.setDirection(squadCreatedDTO.getClubsProfile().getDirection());
        clubsProfile.setPhone(squadCreatedDTO.getClubsProfile().getPhone());
        clubsProfile.setFax(squadCreatedDTO.getClubsProfile().getFax());
        clubsProfile.setWebsiteName(squadCreatedDTO.getClubsProfile().getWebsiteName());
        clubsProfile.setEmailName(squadCreatedDTO.getClubsProfile().getEmailName());
        clubsProfile.setClubsSquadProfile(saved); // PARENT LINK
        clubsProfileRepository.save(clubsProfile);
        return clubsProfile;
    }

    //ClubsSquadDTO
    public ClubsSquadDTO toClubsSquadDTO(ClubsSquadEntity entity) {
        ClubsSquadDTO clubsSquadDTO = new ClubsSquadDTO();
        clubsSquadDTO.setClubsSquadId(entity.getClubsSquadId());
        clubsSquadDTO.setClubsFullName(entity.getClubsFullName());

        //Goal keepers
        List<GoalKeepersDTO> goalKeepersDTOList = entity.getGoalKeepersEntityList().stream().map(
                dto -> {
                    GoalKeepersDTO goalKeepersDTO = new GoalKeepersDTO();
                    goalKeepersDTO.setClubsGoalKeepersId(dto.getClubsGoalKeepersId());
                    goalKeepersDTO.setFirstName(dto.getFirstName());
                    goalKeepersDTO.setLastName(dto.getLastName());
                    goalKeepersDTO.setClubNumber(dto.getClubNumber());
                    return goalKeepersDTO;
                }
        ).collect(Collectors.toList());
        clubsSquadDTO.setGoalKeepers(goalKeepersDTOList);

        //Defenders
        List<DefendersDTO> defendersDTOList = entity.getDefenderEntityList().stream().map(
                dto -> {
                    DefendersDTO defendersDTO = new DefendersDTO();
                    defendersDTO.setClubsDefendersId(dto.getClubsDefendersId());
                    defendersDTO.setFirstName(dto.getFirstName());
                    defendersDTO.setLastName(dto.getLastName());
                    defendersDTO.setClubNumber(dto.getClubNumber());
                    return defendersDTO;
                }
        ).collect(Collectors.toList());
        clubsSquadDTO.setDefenders(defendersDTOList);

        //Midfielders
        List<MidFieldersDTO> midFieldersDTOList = entity.getMidFieldersEntityList().stream().map(
                dto -> {
                    MidFieldersDTO midFieldersDTO = new MidFieldersDTO();
                    midFieldersDTO.setClubsMidfieldersId(dto.getClubsMidFieldersId());
                    midFieldersDTO.setFirstName(dto.getFirstName());
                    midFieldersDTO.setLastName(dto.getLastName());
                    midFieldersDTO.setClubNumber(dto.getClubNumber());
                    return midFieldersDTO;
                }
        ).collect(Collectors.toList());
        clubsSquadDTO.setMidFielders(midFieldersDTOList);

        //Strikers
        List<StrikersDTO> strikersDTOList = entity.getStrikersEntityList().stream().map(
                dto -> {
                    StrikersDTO strikersDTO = new StrikersDTO();
                    strikersDTO.setClubsStrikersId(dto.getClubsStrikersId());
                    strikersDTO.setFirstName(dto.getFirstName());
                    strikersDTO.setLastName(dto.getLastName());
                    strikersDTO.setClubNumber(dto.getClubNumber());
                    return strikersDTO;
                }
        ).collect(Collectors.toList());
        clubsSquadDTO.setStrikers(strikersDTOList);

        // Clubs profile
        ClubsProfileDTO clubsProfileDTO = new ClubsProfileDTO();
        clubsProfileDTO.setClubsProfileId(entity.getClubsProfileEntity().getClubsProfileId());
        clubsProfileDTO.setStadiumName(entity.getClubsProfileEntity().getStadiumName());
        clubsProfileDTO.setFounded(entity.getClubsProfileEntity().getFounded());
        clubsProfileDTO.setClubsColor(entity.getClubsProfileEntity().getClubsColor());
        clubsProfileDTO.setStreet(entity.getClubsProfileEntity().getStreet());
        clubsProfileDTO.setCity(entity.getClubsProfileEntity().getCity());
        clubsProfileDTO.setDirection(entity.getClubsProfileEntity().getDirection());
        clubsProfileDTO.setPhone(entity.getClubsProfileEntity().getPhone());
        clubsProfileDTO.setFax(entity.getClubsProfileEntity().getFax());
        clubsProfileDTO.setWebsiteName(entity.getClubsProfileEntity().getWebsiteName());
        clubsProfileDTO.setEmailName(entity.getClubsProfileEntity().getEmailName());
        clubsSquadDTO.setClubsProfile(clubsProfileDTO);// PARENT LINK

        return clubsSquadDTO;
    }

    /// /////////////////////////////////////////////////////////////////////////////////////////////////
    // UPDATE
    public StrikersEntity toUpdateStrikersEntity(StrikersCreatedDTO strikers, ClubsSquadEntity clubsSquad) {
        StrikersEntity strikersEntity = new StrikersEntity();
        String firstName = strikers.getFirstName();
        String lastName = strikers.getLastName();
        String clubsNumber = strikers.getClubNumber();
        strikersEntity.setCreatedAt(LocalDateTime.now());
        strikersEntity.setClubsSquadInStriker(clubsSquad); // PARENT LINK
        strikersRepository.updateStrikers(strikersEntity.getClubsStrikersId(), firstName, lastName, clubsNumber);
        return strikersEntity;
    }

    // UPDATE
    public MidFieldersEntity toUpdateMidfieldersEntity(MidFieldersCreatedDTO midFielders, ClubsSquadEntity clubsSquad) {
        MidFieldersEntity midFieldersEntity = new MidFieldersEntity();
        String firstName = midFielders.getFirstName();
        String lastName = midFielders.getLastName();
        String clubsNumber = midFielders.getClubNumber();
        midFieldersEntity.setCreatedAt(LocalDateTime.now());
        midFieldersEntity.setClubsSquadInMidfielder(clubsSquad); // PARENT LINK
        midfieldersRepository.updateMidFielders(midFieldersEntity.getClubsMidFieldersId(), firstName, lastName, clubsNumber);
        return midFieldersEntity;
    }

    // UPDATE
    public DefendersEntity toUpdateDefendersEntity(DefendersCreatedDTO defenders, ClubsSquadEntity clubsSquad) {
        DefendersEntity defendersEntity = new DefendersEntity();
        String firstName = defenders.getFirstName();
        String lastName = defenders.getLastName();
        String clubsNumber = defenders.getClubNumber();
        defendersEntity.setCreatedAt(LocalDateTime.now());
        defendersEntity.setClubsSquadInDefendersEntity(clubsSquad); // PARENT LINK
        defendersRepository.updateDefenders(defendersEntity.getClubsDefendersId(), firstName, lastName, clubsNumber);
        return defendersEntity;
    }

    // UPDATE
    public GoalKeepersEntity toUpdateGoalKeeperEntity(GoalKeepersCreatedDTO goalKeepersDTO, ClubsSquadEntity clubsSquad) {
        GoalKeepersEntity goalKeepers = new GoalKeepersEntity();
        String firstName = goalKeepersDTO.getFirstName();
        String lastName = goalKeepersDTO.getLastName();
        String clubsNumber = goalKeepersDTO.getClubNumber();
        goalKeepers.setCreatedAt(LocalDateTime.now());
        goalKeepers.setClubsSquadInGoalKeepers(clubsSquad); // PARENT LINK
        goalKeepersRepository.updateGoalkeepers(goalKeepers.getClubsGoalKeepersId(), firstName, lastName, clubsNumber);
        return goalKeepers;
    }

    // UPDATE
    public ClubsProfileEntity updateClubsProfileEntity(ClubsSquadCreatedDTO squadCreatedDTO, ClubsSquadEntity saved) {
        ClubsProfileEntity clubsProfile = new ClubsProfileEntity();
        String stadiumName = squadCreatedDTO.getClubsProfile().getStadiumName();
        String founded = squadCreatedDTO.getClubsProfile().getFounded();
        String clubsColor = squadCreatedDTO.getClubsProfile().getClubsColor();
        String street = squadCreatedDTO.getClubsProfile().getStreet();
        String city = squadCreatedDTO.getClubsProfile().getCity();
        String direction = squadCreatedDTO.getClubsProfile().getDirection();
        String phone = squadCreatedDTO.getClubsProfile().getPhone();
        String fax = squadCreatedDTO.getClubsProfile().getFax();
        String websiteName = squadCreatedDTO.getClubsProfile().getWebsiteName();
        String emailName = squadCreatedDTO.getClubsProfile().getEmailName();
        clubsProfile.setClubsSquadProfile(saved); // PARENT LINK
        clubsProfileRepository.updateClubsProfile(clubsProfile.getClubsProfileId(), stadiumName, founded, clubsColor, street,
                city, direction, phone, fax, websiteName, emailName);
        return clubsProfile;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////

}
