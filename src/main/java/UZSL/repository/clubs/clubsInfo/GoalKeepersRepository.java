package UZSL.repository.clubs.clubsInfo;

import UZSL.entity.clubs.clubsInfo.GoalKeepersEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GoalKeepersRepository extends JpaRepository<GoalKeepersEntity, String> {

    @Transactional
    @Modifying
    @Query("""
                UPDATE GoalKeepersEntity g 
                SET g.firstName = :firstName, 
                    g.lastName = :lastName, 
                    g.clubNumber = :clubNumber 
                WHERE g.clubsGoalKeepersId = :clubsGoalKeepersId
            """)
    void updateGoalkeepers(@Param("clubsGoalKeepersId") String clubsGoalKeepersId,
                           @Param("firstName") String firstName,
                           @Param("lastName") String lastName,
                           @Param("clubNumber") String clubNumber);

}
