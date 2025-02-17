package org.skuhub.skuhub.api.timetable.controller;

import org.skuhub.skuhub.api.timetable.dto.response.TimetableScheduleResponse;
import org.skuhub.skuhub.api.timetable.service.TimetableScheduleService;
import org.skuhub.skuhub.model.timetable.TimetableScheduleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/timetable")
public class TimetableController {

    @Autowired
    private TimetableScheduleService timetableScheduleService;

    @GetMapping("/schedule/class")
    public ResponseEntity<?> getAllSchedules() {
        List<TimetableScheduleEntity> schedules = timetableScheduleService.getAllSchedules();

        // 스케줄 데이터가 없을 경우 404 반환
        if (schedules.isEmpty()) {
            return ResponseEntity.status(404).body(
                    Map.of(
                            "code", 404,
                            "message", "시간표를 찾을 수 없습니다.",
                            "result", null,
                            "isSuccess", false
                    )
            );
        }

        // DTO 변환
        List<TimetableScheduleResponse> responseList = schedules.stream()
                .map(TimetableScheduleResponse::fromEntity)
                .toList();

        return ResponseEntity.ok(
                Map.of(
                        "code", 200,
                        "message", "전체 시간표를 성공적으로 가져왔습니다.",
                        "result", responseList
                )
        );
    }
}
