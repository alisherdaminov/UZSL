package UZSL.domain.repository.match;

import UZSL.domain.model.entity.match.MatchEntity;
import UZSL.domain.model.entity.match.MatchLogoEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchLogoRepository extends JpaRepository<MatchLogoEntity, String> {

    @Transactional
    @Modifying
    @Query("UPDATE MatchLogoEntity SET  teamsEntity.homeTeamEntity.homeTeamId =?2 WHERE userEntity.userId =?1")
    void updateHomeTeamLogo(Integer userId, String homeTeamId);

    @Transactional
    @Modifying
    @Query("UPDATE MatchLogoEntity SET  teamsEntity.awayTeamEntity.awayTeamId =?2 WHERE userEntity.userId =?1")
    void updateAwayTeamLogo(Integer userId, String awayTeamId);

    @Transactional
    @Modifying
    @Query("DELETE FROM MatchLogoEntity m WHERE m.id = ?1")
    MatchEntity deleteMatchId(String matchId);

}
