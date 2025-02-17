package org.skuhub.skuhub.api.timetable.service;

import org.skuhub.skuhub.api.timetable.dto.response.TimetableScheduleResponse;
import org.skuhub.skuhub.model.timetable.TimetableScheduleEntity;
import org.skuhub.skuhub.repository.timetable.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TimetableScheduleService {

    private final ScheduleRepository scheduleRepository;

    public TimetableScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public List<TimetableScheduleResponse> getAllSchedules() {
        List<TimetableScheduleEntity> schedules = scheduleRepository.findAll();
        return schedules.stream()
                .map(schedule -> new TimetableScheduleResponse(
                        schedule.getTimetableId(),
                        schedule.getYear(),
                        schedule.getSemester(),
                        schedule.getDepartment(),
                        schedule.getGrade(),
                        schedule.getCompletionType(),
                        schedule.getSubjectCode(),
                        schedule.getClassNumber(),
                        schedule.getSubjectName(),
                        schedule.getCredit(),
                        schedule.getProfessorName(),
                        schedule.getClassTime(),
                        schedule.getClassroom(),
                        schedule.getGeneralArea(),
                        schedule.getCreatedAt()
                ))
                .collect(Collectors.toList());
    }
}