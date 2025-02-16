package org.skuhub.skuhub.api.taxi.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.skuhub.skuhub.api.auth.service.AuthService;
import org.skuhub.skuhub.api.taxi.dto.request.TaxiPostRequest;
import org.skuhub.skuhub.api.taxi.dto.response.TaxiPostResponse;
import org.skuhub.skuhub.common.utills.jwt.dto.JwtDto;
import org.skuhub.skuhub.model.taxi.TaxiShareJpaEntity;
import org.skuhub.skuhub.repository.taxi.TaxiShareRepository;
import org.springframework.http.HttpStatus;
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
@RequestMapping(value = "/taxi/posts")
@Slf4j
public class TaxiPostController {

    private final TaxiShareRepository taxiShareRepository;
    private final AuthService authService;

    @Operation(summary = "게시글 작성", description = "택시합승 게시글을 올리는 API")
    @PostMapping("")
    public BaseResponse<String> postsTaxiShare(@RequestBody TaxiPostRequest request) {
        log.info("Received post request with userKey: {}", request.getUserKey());
        log.info("Received post request with Title: {}", request.getTitle());

        TaxiShareJpaEntity entity = new TaxiShareJpaEntity();
        entity.setId(request.getUserKey());
        entity.setTitle(request.getTitle());
        entity.setDepartureLocation(request.getDepartureLocation());
        entity.setHeadCount(request.getHeadCount());
        entity.setRideTime(request.getRideTime());
        entity.setDescription(request.getDescription());

        try {
            TaxiShareJpaEntity saved = taxiShareRepository.save(entity);
            log.info("TaxiShare saved with userKey: {}", saved.getUserKey());
            return new BaseResponse<>(true, "200", "택시합승 게시글 저장 성공", OffsetDateTime.now(), "게시글 작성 성공");
        } catch (Exception e) {
            log.error("Error saving TaxiShare: {}", e.getMessage());
            return new BaseResponse<>(false, "500", "게시글 저장 실패", OffsetDateTime.now(), e.getMessage());
        }


    }
}
