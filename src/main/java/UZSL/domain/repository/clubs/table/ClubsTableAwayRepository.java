package UZSL.domain.repository.clubs.table;

import UZSL.domain.model.entity.clubs.table.ClubsTableAwayEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubsTableAwayRepository extends JpaRepository<ClubsTableAwayEntity, String> {

}
