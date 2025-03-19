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

    @PersistenceContext
    private final EntityManager entityManager;

    public SampleDslRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Page<SampleJpaEntity> findSampleWithCustomLogic(String keyword, Pageable pageable) {
        // 'name'을 사용하여 검색
        String jpql = "SELECT s FROM SampleJpaEntity s WHERE s.name LIKE :keyword";
        TypedQuery<SampleJpaEntity> query = entityManager.createQuery(jpql, SampleJpaEntity.class);
        query.setParameter("keyword", "%" + keyword + "%");

        // 전체 데이터의 개수를 계산하는 쿼리
        String countJpql = "SELECT COUNT(s) FROM SampleJpaEntity s WHERE s.name LIKE :keyword";
        TypedQuery<Long> countQuery = entityManager.createQuery(countJpql, Long.class);
        countQuery.setParameter("keyword", "%" + keyword + "%");
        long totalRows = countQuery.getSingleResult();

        // 페이징 처리
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<SampleJpaEntity> results = query.getResultList();

        return new PageImpl<>(results, pageable, totalRows);
    }
}
