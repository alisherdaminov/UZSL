package UZSL.domain.repository.stats;

import UZSL.domain.model.entity.stats.StatsPlayersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatsPlayerRepository extends JpaRepository<StatsPlayersEntity, String> {
}
