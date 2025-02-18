package org.skuhub.skuhub.repository.users;

import org.skuhub.skuhub.model.user.UserInfoJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserInfoJpaEntity, Integer> {
    Optional<UserInfoJpaEntity> findByEmail(String email);

    Optional<UserInfoJpaEntity> findByUserId(String userId);  // 추가된 메서드

    boolean existsByUserId(String userId);

}
