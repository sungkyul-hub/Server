package org.skuhub.skuhub.api.timetable.controller;

import lombok.RequiredArgsConstructor;
import org.skuhub.skuhub.api.timetable.dto.request.CompletionRequest;
import org.skuhub.skuhub.api.timetable.service.CompletionService;
import org.skuhub.skuhub.common.response.BaseResponse;
import org.skuhub.skuhub.model.timetable.CompletionEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.skuhub.skuhub.api.timetable.dto.response.CompletionResponse;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/timetable")
@RequiredArgsConstructor
public class CompletionController {

    private final CompletionService completionService;

    /**
     * 사용자 이수구분 입력 API
     */
    @PostMapping("/completion/post")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<BaseResponse<Map<String, Object>>> postCompletion(@RequestBody CompletionRequest request) {
        if (request.getUserKey() == null || request.getEarnedCredits() == null || request.getGraduationCredits() == null) {
            return ResponseEntity.status(400).body(
                    new BaseResponse<>(400, "필수 값이 입력되지 않았습니다.", null, false)
            );
        }

        CompletionEntity savedData = completionService.saveCompletionData(request);

        Map<String, Object> result = new HashMap<>();
        result.put("user_key", savedData.getUserInfoTb().getUserKey());
        result.put("updated_at", DateTimeFormatter.ISO_INSTANT.format(savedData.getUpdatedAt()));

        return ResponseEntity.ok(new BaseResponse<>(201, "이수구분 정보가 성공적으로 입력되었습니다.", result, true));
    }

    /**
     * 이수구분 정보 수정 API
     */
    @PostMapping("/completion/edit")
    public ResponseEntity<BaseResponse<CompletionResponse>> editCompletion(@RequestBody CompletionRequest request) {
        CompletionEntity completion = completionService.updateCompletionData(request);

        CompletionResponse response = new CompletionResponse(completion.getUserInfoTb().getUserKey(), completion.getUpdatedAt());

        return ResponseEntity.status(201).body(
                new BaseResponse<>(201, "이수구분 정보가 성공적으로 수정되었습니다.", response, true)
        );
    }
}
