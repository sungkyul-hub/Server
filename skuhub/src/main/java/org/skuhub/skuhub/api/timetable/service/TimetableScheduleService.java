package org.skuhub.skuhub.api.timetable.service;

import org.skuhub.skuhub.model.timetable.TimetableScheduleEntity;
import org.skuhub.skuhub.repository.timetable.TimetableScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimetableScheduleService {

    @Autowired
    private TimetableScheduleRepository timetableScheduleRepository;

    // 전체 시간표 조회 (모든 강의 관련 데이터 반환)
    public List<TimetableScheduleEntity> getAllSchedules() {
        return timetableScheduleRepository.findAll();
    }
}
