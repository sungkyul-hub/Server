package org.skuhub.skuhub.api.timetable.controller;

import lombok.RequiredArgsConstructor;
import org.skuhub.skuhub.api.timetable.dto.request.UserTimetableRequest;
import org.skuhub.skuhub.api.timetable.dto.response.UserTimetableResponse;
import org.skuhub.skuhub.api.timetable.service.UserTimetableService;
import org.skuhub.skuhub.common.response.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/timetable/user")
@RequiredArgsConstructor
public class UserTimetableController {

    private final UserTimetableService userTimetableService;

    @PostMapping("/post")
    @PreAuthorize("hasAuthority('USER')") // JWT 인증 필요
    public ResponseEntity<BaseResponse<?>> createUserTimetable(@RequestBody UserTimetableRequest request) {
        userTimetableService.createUserTimetable(request);
        return ResponseEntity.status(201).body(
                new BaseResponse<>(201, "시간표를 성공적으로 등록했습니다.", request.getTimetableId(), true)
        );
    }

    @GetMapping("/user_time")
    @PreAuthorize("hasAuthority('USER')") // JWT 인증 필요
    public ResponseEntity<BaseResponse<List<UserTimetableResponse>>> getUserTimetable(@RequestParam("user_key") Integer userKey) {
        List<UserTimetableResponse> responseList = userTimetableService.getUserTimetable(userKey);

        if (responseList.isEmpty()) {
            return ResponseEntity.status(404).body(
                    new BaseResponse<>(404, "개인 시간표를 찾을 수 없습니다.", null, false)
            );
        }

        return ResponseEntity.ok(new BaseResponse<>(200, "사용자의 시간표를 성공적으로 가져왔습니다.", responseList, true));
    }
}
