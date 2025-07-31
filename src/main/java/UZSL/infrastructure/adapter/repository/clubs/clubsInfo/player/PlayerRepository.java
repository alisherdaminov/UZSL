package UZSL.infrastructure.adapter.repository.clubs.clubsInfo.player;

import UZSL.domain.model.entity.clubs.clubsInfo.player_info.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<PlayerEntity, String> {


}
