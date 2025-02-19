package org.skuhub.skuhub.repository.notice;
import org.skuhub.skuhub.api.notice.dto.response.NoticeResponse;
import org.skuhub.skuhub.model.notice.NoticeJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoticeRepository extends JpaRepository<NoticeJpaEntity, Long> {
    @Query("SELECT p FROM NoticeJpaEntity p WHERE p.title LIKE CONCAT('%', :keyword, '%')")
    List<NoticeJpaEntity> findByTitle(@Param("keyword") String keyword);
}
