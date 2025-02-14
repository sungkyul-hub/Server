package org.skuhub.skuhub.repository.sample;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.skuhub.skuhub.model.sample.SampleJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SampleDslRepositoryImpl implements SampleDslRepository {

    @PersistenceContext // EntityManager 주입
    private final EntityManager entityManager;

    public SampleDslRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Page<SampleJpaEntity> findSampleWithCustomLogic(String keyword, Pageable pageable) {
        String jpql = "SELECT s FROM SampleJpaEntity s WHERE s.text LIKE :keyword";
        TypedQuery<SampleJpaEntity> query = entityManager.createQuery(jpql, SampleJpaEntity.class);
        query.setParameter("keyword", "%" + keyword + "%");

        int totalRows = query.getResultList().size();
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<SampleJpaEntity> results = query.getResultList();

        return new PageImpl<>(results, pageable, totalRows);
    }
}
