package UZSL.repository.clubs.clubsInfo.player;

import UZSL.entity.clubs.clubsInfo.player_info.PlayerCareerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerCareerRepository extends JpaRepository<PlayerCareerEntity, String> {


}
