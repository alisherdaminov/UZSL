package UZSL.repository.table;

import UZSL.entity.table.ClubsTableHomeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubsTableHomeRepository extends JpaRepository<ClubsTableHomeEntity, String> {

}
