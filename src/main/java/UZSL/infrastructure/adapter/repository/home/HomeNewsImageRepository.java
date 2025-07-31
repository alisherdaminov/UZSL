package UZSL.infrastructure.adapter.repository.home;

import UZSL.domain.model.entity.home.HomeImageEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HomeNewsImageRepository extends JpaRepository<HomeImageEntity, String> {

    @Transactional
    @Modifying
    @Query("UPDATE HomeImageEntity SET homeImageId =?2 WHERE homeNewsEntity.userId =?1")
    void updatePhoto(Integer userId, String homeImageId);


}
