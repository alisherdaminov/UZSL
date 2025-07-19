package UZSL.repository.table;

import UZSL.entity.table.ClubsTableEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClubsTableRepository extends JpaRepository<ClubsTableEntity, String> {

    @Query("SELECT c FROM ClubsTableEntity c " +
            "WHERE c.clubsTableHomeEntity.homeClubName = :homeName " +
            "AND c.clubsTableAwayEntity.awayClubName = :awayName")
    List<ClubsTableEntity> findAllByHomeClubNameAndVisitorClubName(@Param("homeName") String homeName,
                                                                   @Param("awayName") String awayName);

    @Query("""
            SELECT c FROM ClubsTableEntity c
            ORDER BY 
                (COALESCE(c.clubsTableHomeEntity.totalPoints, 0) + COALESCE(c.clubsTableAwayEntity.totalPoints, 0)) DESC,
                (
                    COALESCE(c.clubsTableHomeEntity.goalsOwn, 0) - COALESCE(c.clubsTableHomeEntity.goalsAgainst, 0) +
                    COALESCE(c.clubsTableAwayEntity.goalsOwn, 0) - COALESCE(c.clubsTableAwayEntity.goalsAgainst, 0)
                ) DESC
            """)
    List<ClubsTableEntity> getLeagueTable();


}
