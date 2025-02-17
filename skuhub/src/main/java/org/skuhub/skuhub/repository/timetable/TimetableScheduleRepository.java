package org.skuhub.skuhub.repository.timetable;

import org.jetbrains.annotations.NotNull;
import org.skuhub.skuhub.model.timetable.TimetableScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimetableScheduleRepository extends JpaRepository<TimetableScheduleEntity, Integer> {

    // 전체 시간표 조회 (모든 데이터 반환)
    @NotNull List<TimetableScheduleEntity> findAll();
}
