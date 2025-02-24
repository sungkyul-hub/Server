package org.skuhub.skuhub.api.timetable.controller;

import lombok.RequiredArgsConstructor;
import org.skuhub.skuhub.api.timetable.dto.request.UpdateUserTimetableRequest;
import org.skuhub.skuhub.api.timetable.dto.request.UserTimetableRequest;
import org.skuhub.skuhub.api.timetable.dto.response.UserTimetableResponse;
import org.skuhub.skuhub.api.timetable.service.UserTimetableService;
import org.skuhub.skuhub.common.response.BaseResponse;
import org.skuhub.skuhub.common.utills.jwt.JWTUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.skuhub.skuhub.api.timetable.dto.request.DeleteUserTimetableRequest;
import org.skuhub.skuhub.api.timetable.dto.request.ScoreRequest;
import org.skuhub.skuhub.api.timetable.dto.request.UpdateScoreRequest;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/timetable/")
@RequiredArgsConstructor
public class UserTimetableController {

    private final UserTimetableService userTimetableService;
    private final JWTUtil jwtUtil; // JWT에서 userKey 추출을 위해 추가

    /**
     * 사용자 시간표 등록 API
     */
    @PostMapping("user/post")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<BaseResponse<?>> createUserTimetable(@RequestBody UserTimetableRequest request) {
        userTimetableService.createUserTimetable(request);
        return ResponseEntity.status(201).body(
                new BaseResponse<>(201, "시간표를 성공적으로 등록했습니다.", request.getTimetableId(), true)
        );
    }

    /**
     * 사용자 시간표 조회 API
     */
    @GetMapping("/user_time")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<BaseResponse<List<UserTimetableResponse>>> getUserTimetable(@RequestParam("user_key") Integer userKey) {
        List<UserTimetableResponse> responseList = userTimetableService.getUserTimetable(userKey);

        if (responseList.isEmpty()) {
            return ResponseEntity.status(404).body(
                    new BaseResponse<>(404, "개인 시간표를 찾을 수 없습니다.", null, false)
            );
        }

        return ResponseEntity.ok(new BaseResponse<>(200, "사용자의 시간표를 성공적으로 가져왔습니다.", responseList, true));
    }

    /**
     * 사용자 시간표 수정 API
     */
    @PostMapping("/user/edit")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<BaseResponse<?>> editUserTimetable(@RequestBody UpdateUserTimetableRequest request) {
        if (request.getPersonalKey() == null) {
            return ResponseEntity.status(400).body(
                    new BaseResponse<>(400, "Error: Personal Key is missing! Please provide a valid personal_key.", null, false)
            );
        }

        boolean isUpdated = userTimetableService.updateUserTimetable(request);

        if (!isUpdated) {
            return ResponseEntity.status(400).body(
                    new BaseResponse<>(400, "필수 값이 입력되지 않았습니다.", null, false)
            );
        }

        return ResponseEntity.ok(
                new BaseResponse<>(201, "시간표를 성공적으로 수정했습니다.", request.getTimetableId(), true)
        );
    }

    /**
     * 사용자 시간표 삭제 API
     */
    @DeleteMapping("/user/delete")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<BaseResponse<?>> deleteUserTimetable(@RequestBody DeleteUserTimetableRequest request) {
        if (request.getPersonalKey() == null) {
            return ResponseEntity.status(400).body(
                    new BaseResponse<>(400, "Error: Personal Key is missing! Please provide a valid personal_key.", null, false)
            );
        }

        boolean isDeleted = userTimetableService.deleteUserTimetable(request.getPersonalKey());

        if (!isDeleted) {
            return ResponseEntity.status(404).body(
                    new BaseResponse<>(404, "시간표를 찾을 수 없습니다.", null, false)
            );
        }

        return ResponseEntity.ok(
                new BaseResponse<>(200, "시간표를 성공적으로 삭제했습니다.", null, true)
        );
    }

    /**
     * 사용자 성적 입력 API
     */
    @PostMapping("/score/post")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<BaseResponse<?>> addScore(@RequestBody ScoreRequest request) {
        boolean isUpdated = userTimetableService.addScoreToUserTimetable(
                request.getPersonalKey(),
                request.getScore(),
                request.getMajorStatus()
        );

        if (!isUpdated) {
            return ResponseEntity.status(404).body(
                    new BaseResponse<>(404, "시간표를 찾을 수 없습니다.", null, false)
            );
        }

        return ResponseEntity.ok(
                new BaseResponse<>(201, "성적을 성공적으로 입력했습니다.", request.getPersonalKey(), true)
        );
    }

    /**
     * 성적 수정 API
     */
    @PostMapping("/score/edit")
    @PreAuthorize("hasAuthority('USER')") // JWT 인증 필요
    public ResponseEntity<BaseResponse<?>> editScore(@RequestBody UpdateScoreRequest request) {
        if (request.getPersonalKey() == null || request.getScore() == null) {
            return ResponseEntity.status(400).body(
                    new BaseResponse<>(400, "필수 값이 입력되지 않았습니다.", null, false)
            );
        }

        boolean isUpdated = userTimetableService.updateUserScore(request);
        if (!isUpdated) {
            return ResponseEntity.status(404).body(
                    new BaseResponse<>(404, "시간표를 찾을 수 없습니다.", null, false)
            );
        }

        return ResponseEntity.ok(
                new BaseResponse<>(201, "성적을 성공적으로 수정했습니다.", request.getPersonalKey(), true)
        );
    }
}
