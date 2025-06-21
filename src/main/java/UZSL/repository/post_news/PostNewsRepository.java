package UZSL.repository.post_news;

import UZSL.entity.post_news.PostNewsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostNewsRepository extends JpaRepository<PostNewsEntity, String> {

    Page<PostNewsEntity> findByUserIdAndOrderByCreatedAtDesc(Integer userId, Pageable pageable);
}
