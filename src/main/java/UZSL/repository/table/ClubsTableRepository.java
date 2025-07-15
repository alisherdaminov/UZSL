package UZSL.repository.table;

import UZSL.entity.table.ClubsTableEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClubsTableRepository extends JpaRepository<ClubsTableEntity, String> {

//    Optional<ClubsTableEntity> findByClubName(String clubName);
//
//    @Query("SELECT c from ClubsTableEntity c ORDER BY (c.won * 3 + c.drawn) DESC, (c.goalsOwn - c.goalsAgainst) DESC")
//    List<ClubsTableEntity> getFullClubsTable();
}
