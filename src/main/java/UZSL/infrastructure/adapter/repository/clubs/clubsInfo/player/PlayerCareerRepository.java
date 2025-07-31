package UZSL.infrastructure.adapter.repository.clubs.clubsInfo.player;

import UZSL.domain.model.entity.clubs.clubsInfo.player_info.PlayerCareerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerCareerRepository extends JpaRepository<PlayerCareerEntity, String> {


}
