package org.skuhub.skuhub.repository.notice;
import org.skuhub.skuhub.model.notice.NoticeJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoticeRepository extends JpaRepository<NoticeJpaEntity, Long> {

}
