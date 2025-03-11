package org.skuhub.skuhub.api.push.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.skuhub.skuhub.api.push.dto.TokenSaveRequest;
import org.skuhub.skuhub.api.push.service.PushServiceImpl;
import org.skuhub.skuhub.common.response.BaseResponse;
import org.skuhub.skuhub.common.utills.jwt.JWTUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/fcm")
@Slf4j
public class PushController {
    private final PushServiceImpl pushService;
    private final JWTUtil jwtUtil;


    @Operation(summary = "fcm 토큰 저장", description = "fcm 토큰 저장하는 API")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/save")
    public BaseResponse<String> saveToken(HttpServletRequest request,
                                          @RequestBody TokenSaveRequest tokenRequest) {
        String userId = jwtUtil.getUserId(request);
        return pushService.saveToken(userId, tokenRequest.getToken());
    }

    @Operation(summary = "fcm 토큰 삭제", description = "fcm 토큰 삭제하는 API")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/delete")
    public BaseResponse<String> deleteToken(HttpServletRequest request) {
        String userId = jwtUtil.getUserId(request);
        return pushService.deleteToken(userId);
    }

    @Operation(summary = "키워드 알림 전송", description = "키워드 알림 전송하는 API")
    public BaseResponse<String> pushKeywordAlarm() {
        return null;
    }

    @Operation(summary = "택시 참가 전송", description = "택시 게시글에 모구 참여했을 때 전송하는 API")
    public BaseResponse<String> pushTaxiJoinAlarm(Long postId) {
        return null;
    }

//    @Operation(summary = "택시 게시글 댓글 전송", description = "택시 게시글에 댓글이 달렸을 때 전송하는 API")
//    public boolean pushTaxiCommentAlarm(Long postId) throws IOException {
//        return pushService.pushTaxiCommentAlarm(postId);
//    }

}
