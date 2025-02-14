package org.skuhub.skuhub.api.Sample.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.skuhub.skuhub.api.Sample.dto.request.SamplePostRequest;
import org.skuhub.skuhub.api.Sample.dto.response.SampleResponse;
import org.skuhub.skuhub.model.sample.SampleJpaEntity;
import org.skuhub.skuhub.repository.sample.SampleRepository;
import org.skuhub.skuhub.common.response.BaseResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.OffsetDateTime;
import java.util.List;  // List를 임포트

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/sample")
@Slf4j  // Slf4j 로깅을 사용합니다.
public class SampleController {
    private final SampleRepository sampleRepository;

    @PostMapping("")
    public BaseResponse postSample(@RequestBody SamplePostRequest request) {
        log.info("Received post request with userId: {}", request.getUserId());  // 로그 추가

        SampleJpaEntity entity = new SampleJpaEntity();
        entity.setUserId(request.getUserId());
        entity.setEmail(request.getEmail());
        entity.setPassword(request.getPassword());
        entity.setYear(request.getYear());
        entity.setDepartment(request.getDepartment());
        entity.setName(request.getName());

        // 현재 시간을 OffsetDateTime으로 설정
        entity.setCreatedAt(OffsetDateTime.now());
        entity.setWithdrawalDate(OffsetDateTime.now());  // 현재 시간을 withdrawalDate로 설정

        SampleJpaEntity saved = sampleRepository.save(entity);

        SampleResponse response = new SampleResponse(
                saved.getUserKey(),
                saved.getUserId(),
                saved.getEmail(),
                saved.getYear(),
                saved.getDepartment(),
                saved.getName(),
                saved.getCreatedAt(),
                saved.getWithdrawalDate()
        );

        log.info("Sample saved with userKey: {}", saved.getUserKey());  // 로그 추가

        return new BaseResponse<>(true, "200", "샘플 저장 성공", OffsetDateTime.now(), response);
    }

    @GetMapping("")
    public BaseResponse getSample(
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "page", defaultValue = "1") int page
    ) {
        log.info("Received get request for page {} with size {}", page, size);  // 로그 추가

        Pageable pageable = PageRequest.of(page - 1, size);
        List<SampleJpaEntity> entityList = sampleRepository.findAll(pageable).getContent();

        List<SampleResponse> responseList = entityList.stream()
                .map(entity -> new SampleResponse(
                        entity.getUserKey(),
                        entity.getUserId(),
                        entity.getEmail(),
                        entity.getYear(),
                        entity.getDepartment(),
                        entity.getName(),
                        entity.getCreatedAt(),
                        entity.getWithdrawalDate()
                ))
                .toList();

        log.info("Returning {} samples", responseList.size());  // 로그 추가

        return new BaseResponse<>(true, "200", "샘플 호출 성공", OffsetDateTime.now(), responseList);
    }
}
