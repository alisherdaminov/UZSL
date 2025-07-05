package UZSL.repository.match;

import UZSL.entity.match.MatchEntity;
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
    @Query("UPDATE MatchLogoEntity SET  matchEntity.homeTeamLogoId =?2 WHERE userEntity.userId =?1")
    void updateHomeTeamLogo(Integer userId, String homeTeamLogoId);

    @Transactional
    @Modifying
    @Query("UPDATE MatchLogoEntity SET  matchEntity.visitorLogoId =?2 WHERE userEntity.userId =?1")
    void updateVisitorTeamLogo(Integer userId, String homeTeamLogoId);


    @Transactional
    @Modifying
    @Query("DELETE FROM MatchLogoEntity m WHERE m.id = ?1")
    MatchEntity deleteMatchId(String matchId);

}
