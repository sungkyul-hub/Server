package org.skuhub.skuhub.repository.timetable;

import org.skuhub.skuhub.model.timetable.TimetableScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<TimetableScheduleEntity, Long> {
}