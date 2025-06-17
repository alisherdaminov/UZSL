package UZSL.repository;

import UZSL.entity.PostNewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostNewsRepository extends JpaRepository<PostNewsEntity, String> {
}
