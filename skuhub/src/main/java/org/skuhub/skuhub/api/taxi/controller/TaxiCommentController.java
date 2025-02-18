package org.skuhub.skuhub.api.taxi.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.skuhub.skuhub.api.taxi.dto.request.TaxiCommentRequest;
import org.skuhub.skuhub.api.taxi.service.TaxiCommentServiceImpl;
import org.skuhub.skuhub.common.utills.jwt.JWTUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.skuhub.skuhub.common.response.BaseResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/taxi/comment")
@Slf4j
public class TaxiCommentController {

    private final TaxiCommentServiceImpl taxiCommentServiceImpl;
    private final JWTUtil jwtUtil;

    @Operation(summary = "댓글 작성", description = "택시합승 게시글에 댓글을 작성하는 API")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("")
    public BaseResponse<String> postTaxiComment(@RequestBody TaxiCommentRequest taxiCommentRequest, HttpServletRequest request) {
        String userId = jwtUtil.getUserId(request);
        return taxiCommentServiceImpl.postTaxiComment(taxiCommentRequest, userId);
    }
}
