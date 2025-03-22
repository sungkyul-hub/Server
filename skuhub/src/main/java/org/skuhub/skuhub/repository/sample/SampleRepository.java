package org.skuhub.skuhub.repository.sample;

import org.skuhub.skuhub.model.sample.SampleJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SampleRepository extends JpaRepository<SampleJpaEntity, Integer>, SampleDslRepository {
}
