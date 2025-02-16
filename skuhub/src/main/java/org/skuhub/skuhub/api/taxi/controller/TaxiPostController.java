package org.skuhub.skuhub.api.taxi.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.skuhub.skuhub.api.taxi.dto.request.TaxiPostRequest;
import org.skuhub.skuhub.api.taxi.service.TaxiPostService;
import org.skuhub.skuhub.common.enums.exception.ErrorCode;
import org.skuhub.skuhub.common.utills.jwt.JWTUtil;
import org.skuhub.skuhub.exceptions.CustomException;
import org.skuhub.skuhub.model.taxi.TaxiShareJpaEntity;
import org.skuhub.skuhub.repository.taxi.TaxiShareRepository;
import org.skuhub.skuhub.repository.users.UserInfoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.skuhub.skuhub.common.response.BaseResponse;

import java.time.OffsetDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/taxi/posts")
@Slf4j
public class TaxiPostController {

    private final TaxiShareRepository taxiShareRepository;
    private final UserInfoRepository userInfoRepository;
    private final JWTUtil jwtUtil;
    private final TaxiPostService taxiPostService;

    @Operation(summary = "게시글 작성", description = "택시합승 게시글을 올리는 API")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("")
    public BaseResponse<String> postsTaxiShare(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody TaxiPostRequest request) {
        log.info(authorizationHeader);
        log.info("Received post request with Title: {}", request.getTitle());

        // TaxiPostService의 인스턴스를 사용하여 메소드 호출
        return taxiPostService.postTaxiShare(request, authorizationHeader);
    }

    @Operation(summary = "게시글 조회", description = "택시합승 게시글들을 조회하는 API")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("")
    public BaseResponse<List<TaxiShareJpaEntity>> getTaxiShare(){

        List<TaxiShareJpaEntity> taxiShares = taxiShareRepository.findAll(); // 모든 게시글 조회
        if(taxiShares.isEmpty()) {
            throw new CustomException(ErrorCode.NotFound, "게시글이 존재하지 않습니다.", HttpStatus.NOT_FOUND);
        }

        return new BaseResponse<List<TaxiShareJpaEntity>>(true, "200", "택시합승 게시글 조회 성공", OffsetDateTime.now(), taxiShares);

    }

//        return taxiPostService.getTaxiShare();
}
