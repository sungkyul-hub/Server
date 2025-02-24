package org.skuhub.skuhub.api.timetable.controller;

import lombok.RequiredArgsConstructor;
import org.skuhub.skuhub.api.timetable.dto.response.PersonalTimetableResponse;
import org.skuhub.skuhub.api.timetable.service.PersonalTimetableService;
import org.skuhub.skuhub.common.response.BaseResponse;
import org.skuhub.skuhub.model.timetable.PersonalTimetableEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/timetable")
@RequiredArgsConstructor
public class PersonalTimetableController {

    private final PersonalTimetableService personalTimetableService;

    /**
     * 사용자의 개인 시간표 조회 API
     * @param userKey 사용자 고유 키 (쿼리 파라미터)
     * @return 개인 시간표 목록
     */
    @GetMapping("/user/time")
    public ResponseEntity<BaseResponse<List<PersonalTimetableResponse>>> getUserTimetable(
            @RequestParam("user_key") Integer userKey) {

        List<PersonalTimetableEntity> timetable = personalTimetableService.getUserTimetable(userKey);

        // 개인 시간표가 없을 경우 404 반환
        if (timetable.isEmpty()) {
            return ResponseEntity.status(404).body(
                    new BaseResponse<>(404, "개인 시간표를 찾을 수 없습니다.", null, false)
            );
        }

        // 엔티티를 DTO로 변환하여 반환
        List<PersonalTimetableResponse> responseList = timetable.stream()
                .map(PersonalTimetableResponse::fromEntity)
                .toList();

        return ResponseEntity.ok(
                new BaseResponse<>(200, "사용자의 시간표를 성공적으로 가져왔습니다.", responseList, true)
        );
    }
}
