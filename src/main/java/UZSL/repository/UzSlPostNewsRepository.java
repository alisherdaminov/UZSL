package UZSL.repository;

import UZSL.entity.UzSLPostNewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UzSlPostNewsRepository extends JpaRepository<UzSLPostNewsEntity, String> {
}
