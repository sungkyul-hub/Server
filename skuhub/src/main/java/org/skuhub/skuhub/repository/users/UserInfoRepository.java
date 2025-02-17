package org.skuhub.skuhub.repository.users;

import org.skuhub.skuhub.model.user.UserInfoJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfoJpaEntity, Long> {
    boolean existsByEmail(String email);
    Optional<UserInfoJpaEntity> findByEmail(String email);
    Optional<UserInfoJpaEntity> findByUserId(String userId); // userId로 찾는 메소드
    UserInfoJpaEntity findByName(String name);
}

