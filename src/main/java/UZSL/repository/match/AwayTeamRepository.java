package UZSL.repository.match;

import UZSL.entity.match.AwayTeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AwayTeamRepository extends JpaRepository<AwayTeamEntity, String> {
}
