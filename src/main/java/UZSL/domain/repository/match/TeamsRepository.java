package UZSL.domain.repository.match;

import UZSL.domain.model.entity.match.TeamsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
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

    @Query("SELECT t FROM TeamsEntity t LEFT JOIN FETCH t.homeTeamEntity LEFT JOIN FETCH t.awayTeamEntity")
    List<TeamsEntity> findAllWithTeams();

    Optional<TeamsEntity> findByHomeTeamEntity_HomeTeamIdAndAwayTeamEntity_AwayTeamId(String homeTeamId, String awayTeamId);



}
