package UZSL.repository.clubs.clubsInfo;

import UZSL.entity.clubs.clubsInfo.ClubsProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ClubsProfileRepository extends JpaRepository<ClubsProfileEntity, String> {

    @Transactional
    @Modifying
    @Query("""
                UPDATE ClubsProfileEntity c 
                SET c.stadiumName = :stadiumName, 
                    c.founded = :founded, 
                    c.clubsColor = :clubsColor, 
                    c.street = :street, 
                    c.city = :city, 
                    c.direction = :direction, 
                    c.phone = :phone, 
                    c.fax = :fax, 
                    c.websiteName = :websiteName, 
                    c.emailName = :emailName 
                WHERE c.clubsProfileId = :clubsProfileId
            """)
    void updateClubsProfile(@Param("clubsProfileId") String clubsProfileId,
                            @Param("stadiumName") String stadiumName,
                            @Param("founded") String founded,
                            @Param("clubsColor") String clubsColor,
                            @Param("street") String street,
                            @Param("city") String city,
                            @Param("direction") String direction,
                            @Param("phone") String phone,
                            @Param("fax") String fax,
                            @Param("websiteName") String websiteName,
                            @Param("emailName") String emailName);

}
