package UZSL.infrastructure.adapter.repository.clubs.clubsInfo;

import UZSL.domain.model.entity.clubs.clubsInfo.MidFieldersEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MidfieldersRepository extends JpaRepository<MidFieldersEntity, String> {

    @Transactional
    @Modifying
    @Query("""
                UPDATE MidFieldersEntity m 
                SET m.firstName = :firstName, 
                    m.lastName = :lastName, 
                    m.clubNumber = :clubNumber 
                WHERE m.clubsMidFieldersId = :clubsMidFieldersId
            """)
    void updateMidFielders(@Param("clubsMidFieldersId") String clubsMidFieldersId,
                           @Param("firstName") String firstName,
                           @Param("lastName") String lastName,
                           @Param("clubNumber") String clubNumber);
}
