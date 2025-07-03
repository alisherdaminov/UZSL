package UZSL.repository.match;

import UZSL.entity.match.MatchLogoEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchLogoRepository extends JpaRepository<MatchLogoEntity, String> {

    @Transactional
    @Modifying
    @Query("UPDATE MatchLogoEntity SET matchLogoId =?2 WHERE userEntity.userId =?1")
    void updateMatchLogo(Integer userId, String matchLogoId);


}
