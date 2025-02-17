package org.skuhub.skuhub.api.taxi.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.skuhub.skuhub.api.taxi.dto.request.TaxiCommentRequest;
import org.skuhub.skuhub.api.taxi.service.TaxiCommentService;
import org.skuhub.skuhub.api.taxi.service.TaxiPostService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.skuhub.skuhub.common.response.BaseResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.OffsetDateTime;
import java.util.List;  // List를 임포트

@RestController
@RequiredArgsConstructor
@RequestMapping(name = "/api/v1/taxi/comment")
@Slf4j
public class TaxiCommentController {

    private final TaxiCommentService taxiCommentService;

    @Operation(summary = "댓글 작성", description = "택시합승 게시글에 댓글을 작성하는 API")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("")
    public BaseResponse<String> postTaxiComment(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody TaxiCommentRequest request) {

        return taxiCommentService.postTaxiComment(request, authorizationHeader);

    }
}
