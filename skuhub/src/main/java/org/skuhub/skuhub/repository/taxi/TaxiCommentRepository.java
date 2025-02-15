package org.skuhub.skuhub.repository.taxi;

import org.skuhub.skuhub.model.taxi.TaxiCommentJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxiCommentRepository extends JpaRepository<TaxiCommentJpaEntity, Integer> {
}
