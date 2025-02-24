package org.skuhub.skuhub.api.schedule.controller;

import lombok.RequiredArgsConstructor;
import org.skuhub.skuhub.api.schedule.dto.response.ScheduleResponse;
import org.skuhub.skuhub.api.schedule.service.ScheduleService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/schedule")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    // 날짜 기반 공지사항 검색 API
    @GetMapping("/contents")
    public ResponseEntity<?> getNoticesByDate(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<ScheduleResponse> notices = scheduleService.getNoticesByDate(date);
        if (notices.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("code", 404, "message", "공지사항을 찾을 수 없습니다."));
        }
        return ResponseEntity.ok(Map.of("isSuccess", true, "code", 200, "message", "success!", "result", notices));
    }
}
