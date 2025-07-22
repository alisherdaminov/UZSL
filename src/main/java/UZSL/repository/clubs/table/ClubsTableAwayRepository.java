package UZSL.repository.clubs.table;

import UZSL.entity.clubs.table.ClubsTableAwayEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubsTableAwayRepository extends JpaRepository<ClubsTableAwayEntity, String> {

}
