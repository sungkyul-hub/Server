package org.skuhub.skuhub.api.timetable.service;

import org.skuhub.skuhub.api.timetable.dto.response.TimetableScheduleResponse;
import org.skuhub.skuhub.model.timetable.TimetableScheduleEntity;
import org.skuhub.skuhub.repository.timetable.TimetableScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TimetableScheduleService {

    private final TimetableScheduleRepository timetableScheduleRepository;

    public TimetableScheduleService(TimetableScheduleRepository timetableScheduleRepository) {
        this.timetableScheduleRepository = timetableScheduleRepository;
    }

    public List<TimetableScheduleResponse> getAllSchedules() {
        List<TimetableScheduleEntity> schedules = timetableScheduleRepository.findAll();
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