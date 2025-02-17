package org.skuhub.skuhub.api.taxi.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.skuhub.skuhub.api.taxi.dto.request.TaxiEditRequest;
import org.skuhub.skuhub.api.taxi.dto.request.TaxiPostDeleteRequest;
import org.skuhub.skuhub.api.taxi.dto.request.TaxiPostRequest;
import org.skuhub.skuhub.api.taxi.dto.response.TaxiPostResponse;
import org.skuhub.skuhub.api.taxi.service.TaxiPostServiceImpl;
import org.skuhub.skuhub.common.utills.jwt.JWTUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.skuhub.skuhub.common.response.BaseResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/taxi/posts")
@Slf4j
public class TaxiPostController {

    private final TaxiPostServiceImpl taxiPostServiceImpl;
    private final JWTUtil jwtUtil;

    @Operation(summary = "게시글 작성", description = "택시합승 게시글을 올리는 API")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("")
    public BaseResponse<String> postsTaxiShare(@RequestBody TaxiPostRequest taxiPostRequest, HttpServletRequest request) {

        String userId = jwtUtil.getUserId(request);
        return taxiPostServiceImpl.postTaxiShare(userId, taxiPostRequest);
    }

    @Operation(summary = "게시글 조회", description = "택시합승 게시글들을 조회하는 API")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("")
    public BaseResponse<List<TaxiPostResponse>> getTaxiShare(){

        return taxiPostServiceImpl.getTaxiShare();
    }


    @Operation(summary = "게시글 수정", description = "택시합승 게시글들을 수정하는 API")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/edit")
    public BaseResponse<String> editTaxiShare(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody TaxiEditRequest request) {

        return taxiPostServiceImpl.postEditTaxiShare(request, authorizationHeader);

    }

    @Operation(summary = "게시글 삭제", description = "택시합승 게시글들을 삭제하는 API")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/delete")
    public BaseResponse<String> deleteTaxiShare(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody TaxiPostDeleteRequest request) {

        return taxiPostServiceImpl.deleteTaxiShare(request, authorizationHeader);

    }

    @Operation(summary = "게시글 상세 조회", description = "택시합승 게시글 상세를 조회하는 API")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/contents/{postId}")
    public BaseResponse<TaxiPostResponse> getTaxiShareDetail(@PathVariable Long postId) {

        return taxiPostServiceImpl.getTaxiShareDetail(postId);
    }

}
