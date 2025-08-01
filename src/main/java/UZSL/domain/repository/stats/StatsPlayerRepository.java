package UZSL.domain.repository.stats;

import UZSL.domain.model.entity.stats.StatsPlayersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StatsPlayerRepository extends JpaRepository<StatsPlayersEntity, String> {

    @Query("SELECT s FROM StatsPlayersEntity s WHERE s.statsPlayerId = :statsId ORDER BY s.goals DESC")
    List<StatsPlayersEntity> findByIdAndSortGoalsTop(@Param("statsId") String statsId);


    @Query("SELECT s FROM StatsPlayersEntity s WHERE s.statsPlayerId = :statsId ORDER BY s.assist DESC")
    List<StatsPlayersEntity> findByIdAndSortAssistTop(@Param("statsId") String statsId);

    @Query("SELECT s FROM StatsPlayersEntity s WHERE s.statsPlayerId = :statsId ORDER BY s.shots DESC")
    List<StatsPlayersEntity> findByIdAndSortShotsTop(@Param("statsId") String statsId);

    @Query("SELECT s FROM StatsPlayersEntity s WHERE s.statsPlayerId = :statsId ORDER BY s.ownGoal DESC")
    List<StatsPlayersEntity> findByIdAndSortOwnGoalsTop(@Param("statsId") String statsId);

    @Query("SELECT s FROM StatsPlayersEntity s WHERE s.statsPlayerId = :statsId ORDER BY s.penalties DESC")
    List<StatsPlayersEntity> findByIdAndSortPenalties(@Param("statsId") String statsId);
}
