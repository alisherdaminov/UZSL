package UZSL.repository.match;

import UZSL.entity.match.HomeTeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HomeTeamRepository extends JpaRepository<HomeTeamEntity, String> {
}
