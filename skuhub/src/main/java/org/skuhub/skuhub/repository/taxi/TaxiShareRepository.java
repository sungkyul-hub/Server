package org.skuhub.skuhub.repository.taxi;

import org.skuhub.skuhub.model.taxi.TaxiShareJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxiShareRepository extends JpaRepository<TaxiShareJpaEntity, Long> {
}
