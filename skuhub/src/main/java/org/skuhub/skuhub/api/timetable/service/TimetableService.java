package org.skuhub.skuhub.api.timetable.service;

import org.skuhub.skuhub.api.timetable.dto.response.ClassScheduleResponse;
import org.skuhub.skuhub.model.timetable.ScheduleEntity;
import org.skuhub.skuhub.repository.timetable.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TimetableService {

    private final ScheduleRepository scheduleRepository;

    public TimetableService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public List<ClassScheduleResponse> getAllSchedules() {
        List<ScheduleEntity> schedules = scheduleRepository.findAll();
        return schedules.stream()
                .map(schedule -> new ClassScheduleResponse(
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