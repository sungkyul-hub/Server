package org.skuhub.skuhub.api.taxi.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.skuhub.skuhub.api.taxi.dto.request.TaxiPostRequest;
import org.skuhub.skuhub.api.taxi.service.TaxiPostService;
import org.skuhub.skuhub.common.utills.jwt.JWTUtil;
import org.skuhub.skuhub.common.utills.jwt.dto.JwtDto;
import org.skuhub.skuhub.repository.taxi.TaxiShareRepository;
import org.skuhub.skuhub.repository.users.UserInfoRepository;
import org.springframework.web.bind.annotation.*;
import org.skuhub.skuhub.common.response.BaseResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/taxi/posts")
@Slf4j
public class TaxiPostController {

    private final TaxiShareRepository taxiShareRepository;
    private final UserInfoRepository userInfoRepository;
    private final JWTUtil jwtUtil;

    @Operation(summary = "게시글 작성", description = "택시합승 게시글을 올리는 API")
    @PostMapping("")
    public BaseResponse<String> postsTaxiShare(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody TaxiPostRequest request) {
        log.info("Received post request with Title: {}", request.getTitle());

        String userId = jwtUtil.getClaims(authorizationHeader).getSubject();
        log.info(userId);

        return TaxiPostService.postTaxiShare(request, authorizationHeader, userInfoRepository, taxiShareRepository);

    }
}
