package UZSL.repository.table;

import UZSL.entity.table.ClubsTableEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubsTableRepository extends JpaRepository<ClubsTableEntity, String> {

}
