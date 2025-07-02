package UZSL.repository.match;

import UZSL.entity.match.ClubsMatchInfoEntity;
import UZSL.entity.match.MatchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubsMatchInfoRepository extends JpaRepository<ClubsMatchInfoEntity, String> {
}
