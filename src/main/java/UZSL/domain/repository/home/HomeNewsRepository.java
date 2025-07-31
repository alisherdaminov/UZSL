package UZSL.domain.repository.home;

import UZSL.domain.model.entity.home.HomeNewsEntity;
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
public interface HomeNewsRepository extends JpaRepository<HomeNewsEntity, String>,
        PagingAndSortingRepository<HomeNewsEntity, String> {

    @Query("SELECT p FROM HomeNewsEntity p WHERE p.userEntity.id = :userId ORDER BY p.createdAt DESC")
    Page<HomeNewsEntity> findByUserIdOrderByCreatedAtDesc(@Param("userId") Integer userId, Pageable pageable);

    @Transactional
    @Modifying
    @Query("UPDATE HomeNewsEntity h SET h.title = :title, h.content = :content," +
            " h.author = :author WHERE h.postNewsId = :postNewsId")
    void updateHomeNews(
            @Param("title") String title,
            @Param("content") String content,
            @Param("author") String author,
            @Param("postNewsId") String postNewsId);

}
