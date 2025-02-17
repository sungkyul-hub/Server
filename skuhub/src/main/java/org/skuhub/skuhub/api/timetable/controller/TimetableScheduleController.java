package org.skuhub.skuhub.api.timetable.controller;

import org.skuhub.skuhub.api.timetable.dto.response.TimetableScheduleResponse;
import org.skuhub.skuhub.api.timetable.service.TimetableScheduleService;
import org.skuhub.skuhub.common.response.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/timetable")
public class TimetableScheduleController {

    private final TimetableScheduleService timetableScheduleService;

    public TimetableScheduleController(TimetableScheduleService timetableScheduleService) {
        this.timetableScheduleService = timetableScheduleService;
    }

    /**
     * 전체 강의 시간표 조회 API
     * @return 강의 시간표 리스트
     */
    @GetMapping("/schedule/class")
    public ResponseEntity<BaseResponse<List<TimetableScheduleResponse>>> getAllClassSchedules() {
        List<TimetableScheduleResponse> schedules;
        schedules = timetableScheduleService.getAllSchedules();

        if (schedules.isEmpty()) {
            return ResponseEntity.status(404)
                    .body(new BaseResponse<>(false, "시간표를 찾을 수 없습니다.", null));
        }

        return ResponseEntity.ok(new BaseResponse<>(true, "전체 시간표를 성공적으로 가져왔습니다.", schedules));
    }
}