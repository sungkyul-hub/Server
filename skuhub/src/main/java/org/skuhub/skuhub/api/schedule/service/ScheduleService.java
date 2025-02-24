package org.skuhub.skuhub.api.schedule.service;

import lombok.RequiredArgsConstructor;
import org.skuhub.skuhub.api.schedule.dto.response.ScheduleResponse;
import org.skuhub.skuhub.repository.schedule.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    public List<ScheduleResponse> getNoticesByDate(LocalDate date) {
        return scheduleRepository.findByNoticeDate(date)
                .stream()
                .map(ScheduleResponse::fromEntity)
                .collect(Collectors.toList());
    }
}

