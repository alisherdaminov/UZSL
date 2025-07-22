package UZSL.repository.clubs.clubsInfo;

import UZSL.entity.clubs.clubsInfo.ClubsSquadEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubsSquadRepository extends JpaRepository<ClubsSquadEntity, String> {


}
