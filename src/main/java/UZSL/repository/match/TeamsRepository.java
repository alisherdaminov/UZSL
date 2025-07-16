package UZSL.repository.match;

import UZSL.entity.match.TeamsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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

}
