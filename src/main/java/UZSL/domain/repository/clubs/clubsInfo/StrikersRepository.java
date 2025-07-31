package UZSL.domain.repository.clubs.clubsInfo;

import UZSL.domain.model.entity.clubs.clubsInfo.StrikersEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StrikersRepository extends JpaRepository<StrikersEntity, String> {

    @Transactional
    @Modifying
    @Query("""
                UPDATE StrikersEntity s 
                SET s.firstName = :firstName, 
                    s.lastName = :lastName, 
                    s.clubNumber = :clubNumber 
                WHERE s.clubsStrikersId = :clubsStrikersId
            """)
    void updateStrikers(@Param("clubsStrikersId") String clubsStrikersId,
                        @Param("firstName") String firstName,
                        @Param("lastName") String lastName,
                        @Param("clubNumber") String clubNumber);

}
