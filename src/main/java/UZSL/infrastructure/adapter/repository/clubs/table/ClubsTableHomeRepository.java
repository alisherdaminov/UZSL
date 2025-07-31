package UZSL.infrastructure.adapter.repository.clubs.table;

import UZSL.domain.model.entity.clubs.table.ClubsTableHomeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubsTableHomeRepository extends JpaRepository<ClubsTableHomeEntity, String> {

}
