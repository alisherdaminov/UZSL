package UZSL.infrastructure.adapter.repository.match;

import UZSL.domain.model.entity.match.AwayTeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AwayTeamRepository extends JpaRepository<AwayTeamEntity, String> {

    @Transactional
    @Modifying
    @Query("UPDATE AwayTeamEntity a SET a.awayGoal = :awayGoal WHERE a.awayTeamId = :awayTeamId")
    void updateAwayGoal(@Param("awayTeamId") String awayTeamId, @Param("awayGoal") int awayGoal);
}
