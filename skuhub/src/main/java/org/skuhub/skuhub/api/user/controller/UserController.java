package org.skuhub.skuhub.api.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.skuhub.skuhub.api.user.dto.request.UpdateUserRequest;
import org.skuhub.skuhub.api.user.dto.request.UserIdRequest;
import org.skuhub.skuhub.api.user.dto.response.UpdatedUserInfoResponse;
import org.skuhub.skuhub.api.user.service.UserService;
import org.skuhub.skuhub.common.response.BaseResponse;
import org.skuhub.skuhub.exceptions.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.skuhub.skuhub.common.enums.exception.ErrorCode;


@RestController
@Tag(name = "User", description = "User API")
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

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

    @Operation(summary = "사용자 정보 변경", description = "학과, 비밀번호, 닉네임를 변경하는 API")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/profile/modify/{userId}")
    public BaseResponse<UpdatedUserInfoResponse> updateUser(@RequestParam String userId, @RequestBody UpdateUserRequest request) {
        try {
            UpdatedUserInfoResponse updatedUserInfoResponse = userService.updateUserInfo(userId, request);
            return new BaseResponse<>(HttpStatus.OK.value(), "사용자 정보가 성공적으로 변경되었습니다.", updatedUserInfoResponse);
        } catch (IllegalArgumentException e) {
            throw new CustomException(ErrorCode.NotFound, e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }



}
