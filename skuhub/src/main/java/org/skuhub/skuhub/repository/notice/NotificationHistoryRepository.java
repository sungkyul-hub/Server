package org.skuhub.skuhub.repository.notice;

import org.skuhub.skuhub.model.notice.NotificationHistoryJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NotificationHistoryRepository extends JpaRepository<NotificationHistoryJpaEntity, Long> {
    Optional<NotificationHistoryJpaEntity> findByNotice(Long noticeId);
}
