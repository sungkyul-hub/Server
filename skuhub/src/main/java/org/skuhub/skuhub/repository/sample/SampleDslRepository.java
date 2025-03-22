package org.skuhub.skuhub.repository.sample;

import org.skuhub.skuhub.model.sample.SampleJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SampleDslRepository {
    Page<SampleJpaEntity> findSampleWithCustomLogic(String keyword, Pageable pageable);
}
