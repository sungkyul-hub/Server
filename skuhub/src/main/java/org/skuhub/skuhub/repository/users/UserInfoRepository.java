package org.skuhub.skuhub.repository.users;

import org.skuhub.skuhub.model.user.UserInfoJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfoJpaEntity, Long> {
    boolean existsByEmail(String email);

    Optional<UserInfoJpaEntity> findByEmail(String email);

    boolean existsByUserId(String userId);

    Optional<UserInfoJpaEntity> findByUserId(String userId);
}

