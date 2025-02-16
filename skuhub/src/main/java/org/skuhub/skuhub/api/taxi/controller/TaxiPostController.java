package org.skuhub.skuhub.api.taxi.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.skuhub.skuhub.api.taxi.dto.request.TaxiPostRequest;
import org.skuhub.skuhub.model.taxi.TaxiShareJpaEntity;
import org.skuhub.skuhub.model.user.UserInfoJpaEntity;
import org.skuhub.skuhub.repository.taxi.TaxiShareRepository;
import org.skuhub.skuhub.repository.users.UserInfoRepository;
import org.springframework.web.bind.annotation.*;
import org.skuhub.skuhub.common.response.BaseResponse;

import java.time.OffsetDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/taxi/posts")
@Slf4j
public class TaxiPostController {

    private final TaxiShareRepository taxiShareRepository;
    private final UserInfoRepository userInfoRepository;

    @Operation(summary = "게시글 작성", description = "택시합승 게시글을 올리는 API")
    @PostMapping("")
    public BaseResponse<String> postsTaxiShare(@RequestBody TaxiPostRequest request) {
        log.info("Received post request with userKey: {}", request.getUserId());
        log.info("Received post request with Title: {}", request.getTitle());

        UserInfoJpaEntity userEntity = userInfoRepository.findByUserId(request.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        log.info("Request received with userKey: {}", request.getUserId());

        TaxiShareJpaEntity entity = new TaxiShareJpaEntity();
        entity.setUserKey(userEntity); // userKey 설정
        entity.setTitle(request.getTitle());
        entity.setDepartureLocation(request.getDepartureLocation());
        entity.setNumberOfPeople(request.getNumberOfPeople());
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
