package UZSL.domain.repository.match;

import UZSL.domain.model.entity.match.HomeTeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface HomeTeamRepository extends JpaRepository<HomeTeamEntity, String> {

    @Transactional
    @Modifying
    @Query("UPDATE HomeTeamEntity h SET h.ownGoal = :ownGoal WHERE h.homeTeamId = :homeTeamId")
    void updateOwnGoal(@Param("homeTeamId") String homeTeamId, @Param("ownGoal") int ownGoal);

}
