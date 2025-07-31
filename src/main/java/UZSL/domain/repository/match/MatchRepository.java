package UZSL.domain.repository.match;

import UZSL.domain.model.entity.match.MatchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchRepository extends JpaRepository<MatchEntity, String> {


    @Query("SELECT COUNT(m) FROM MatchEntity m JOIN m.teamsEntityList c WHERE c.teamsId = :teamsId")
    int countMatchesByClubsMatchInfoId(@Param("teamsId") String teamsId);


}
