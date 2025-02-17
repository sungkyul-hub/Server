package org.skuhub.skuhub.api.taxi.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.skuhub.skuhub.api.taxi.dto.request.TaxiCommentRequest;
import org.skuhub.skuhub.api.taxi.service.TaxiCommentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.skuhub.skuhub.common.response.BaseResponse;

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
