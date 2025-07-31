package UZSL.infrastructure.adapter.repository.clubs.clubsInfo;

import UZSL.domain.model.entity.clubs.clubsInfo.DefendersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface DefendersRepository extends JpaRepository<DefendersEntity, String> {

    @Transactional
    @Modifying
    @Query("""
                UPDATE DefendersEntity d 
                SET d.firstName = :firstName, 
                    d.lastName = :lastName, 
                    d.clubNumber = :clubNumber 
                WHERE d.clubsDefendersId = :clubsDefendersId
            """)
    void updateDefenders(@Param("clubsDefendersId") String clubsDefendersId,
                        @Param("firstName") String firstName,
                        @Param("lastName") String lastName,
                        @Param("clubNumber") String clubNumber);

}
