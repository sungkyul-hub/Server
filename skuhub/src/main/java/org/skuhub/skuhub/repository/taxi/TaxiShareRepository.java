package org.skuhub.skuhub.repository.taxi;

import org.skuhub.skuhub.model.taxi.TaxiShareJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaxiShareRepository extends JpaRepository<TaxiShareJpaEntity, Long> {
    @Query("SELECT ts FROM TaxiShareJpaEntity ts WHERE ts.createdAt >= :startOfDay AND ts.createdAt < :endOfDay")
    List<TaxiShareJpaEntity> findAllByCreatedAtToday(@Param("startOfDay") LocalDateTime startOfDay, @Param("endOfDay") LocalDateTime endOfDay);
}
