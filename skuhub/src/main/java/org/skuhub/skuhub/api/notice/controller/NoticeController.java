package org.skuhub.skuhub.api.notice.controller;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.skuhub.skuhub.api.notice.dto.response.NoticeDetailsResponse;
import org.skuhub.skuhub.api.notice.dto.response.NoticeResponse;
import org.skuhub.skuhub.api.notice.service.NoticeServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.skuhub.skuhub.common.response.BaseResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/notice")
@Slf4j
public class NoticeController {
    private final NoticeServiceImpl noticeServiceImpl;

    @Operation(summary = "공지사항 검색", description = "공지사항을 검색하는 API")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/search")
    public BaseResponse<List<NoticeResponse>> searchNotice(@RequestParam String keyword) {
        log.info("keyword: {}", keyword);
        return noticeServiceImpl.searchNotice(keyword);
    }

    @Operation(summary = "공지사항 카테고리 검색", description = "공지사항의 카테고리를 검색하는 API")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/category/{category}")
    public BaseResponse<List<NoticeResponse>> categoryNotice(@PathVariable String category) {
        log.info("category: {}", category);
        return noticeServiceImpl.categoryNotice(category);
    }

    @Operation(summary = "공지 상세보기", description = "공지를 상세하게 보는 API")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/contents/{noticeId}")
    public BaseResponse<NoticeDetailsResponse> detailNotice(@PathVariable Long noticeId) {
        log.info("noticeId: {}", noticeId);
        return noticeServiceImpl.detailNotice(noticeId);
    }
}
