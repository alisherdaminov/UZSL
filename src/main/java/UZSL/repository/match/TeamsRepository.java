package UZSL.repository.match;

import UZSL.entity.match.TeamsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamsRepository extends JpaRepository<TeamsEntity, String> {
}
