package UZSL.repository.match;

import UZSL.entity.match.TeamsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface TeamsRepository extends JpaRepository<TeamsEntity, String> {

    @Query("""
                SELECT t FROM TeamsEntity t
                WHERE LOWER(TRIM(t.homeTeamEntity.homeTeamName)) = LOWER(TRIM(:homeName))
                  AND LOWER(TRIM(t.awayTeamEntity.awayTeamName)) = LOWER(TRIM(:awayName))
            """)
    Optional<TeamsEntity> findByHomeAndAwayTeamNamesIgnoreCase(@Param("homeName") String homeName,
                                                               @Param("awayName") String awayName);

    @Transactional
    @Modifying
    @Query("UPDATE TeamsEntity t SET t.homeTeamEntity.ownGoal = :ownGoal, t.awayTeamEntity.awayGoal = :awayGoal WHERE t.id = :teamsId")
    void updateTeamGoals(@Param("teamsId") String teamsId,
                         @Param("ownGoal") int ownGoal,
                         @Param("awayGoal") int awayGoal);


}
