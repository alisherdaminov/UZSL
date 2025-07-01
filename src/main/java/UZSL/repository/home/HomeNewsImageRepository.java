package UZSL.repository.home;

import UZSL.entity.home.HomeImageEntity;
import UZSL.entity.home.HomeNewsEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HomeNewsImageRepository extends JpaRepository<HomeImageEntity, String> {

    @Transactional
    @Modifying
    @Query("UPDATE HomeImageEntity SET homeImageId =?2 WHERE homeNewsEntity.userId =?1")
    void updatePhoto(Integer userId, String homeImageId);


}
