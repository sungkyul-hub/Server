package org.skuhub.skuhub.repository.timetable;

import org.skuhub.skuhub.model.timetable.UserInfoTb;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<UserInfoTb, Integer> {
}
