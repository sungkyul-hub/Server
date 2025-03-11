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


}
