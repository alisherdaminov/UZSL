package UZSL.repository.table;

import UZSL.entity.table.ClubsTableAwayEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubsTableAwayRepository extends JpaRepository<ClubsTableAwayEntity, String> {

}
