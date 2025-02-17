package org.skuhub.skuhub.api.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.skuhub.skuhub.api.user.dto.request.UserIdRequest;
import org.skuhub.skuhub.api.user.dto.response.UserIdResponse;
import org.skuhub.skuhub.api.user.service.UserService;
import org.skuhub.skuhub.common.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "User", description = "User API")
@RequestMapping("api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Operation(summary = "유저 아이디 찾기", description = "이메일을 통해 유저 아이디를 찾는 API")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/find-username")
    public BaseResponse<String> findUserId(@RequestBody UserIdRequest request) {
        // 이메일 형식 검사
        if (!request.getEmail().endsWith("@sungkyul.ac.kr")) {
            return new BaseResponse<>(HttpStatus.BAD_REQUEST.value(), "유효하지 않은 이메일 도메인입니다.", "유효하지 않은 이메일 도메인입니다.");
        }

        // 이메일로 유저 아이디 찾기
        String userId = userService.findUserIdByEmail(request.getEmail());

        // 유저가 존재하지 않는 경우 처리
        if (userId == null) {
            return new BaseResponse<>(HttpStatus.NOT_FOUND.value(), "이메일에 해당하는 유저가 없습니다.", "이메일에 해당하는 유저가 없습니다.");
        }

        // 성공적인 유저 아이디 반환
        return new BaseResponse<>(HttpStatus.OK.value(), "성공적으로 유저 아이디를 찾았습니다.", userId);
    }

}
