package UZSL.infrastructure.adapter.repository.stats;

import UZSL.domain.model.entity.stats.StatsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatsRepository extends JpaRepository<StatsEntity, String> {
}
