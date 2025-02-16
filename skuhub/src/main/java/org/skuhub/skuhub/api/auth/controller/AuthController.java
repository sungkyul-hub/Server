package org.skuhub.skuhub.api.auth.controller;

import groovy.util.logging.Slf4j;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.skuhub.skuhub.api.auth.dto.request.LoginRequest;
import org.skuhub.skuhub.api.auth.dto.request.ReissueRequest;
import org.skuhub.skuhub.api.auth.dto.request.SignupRequest;
import org.skuhub.skuhub.api.auth.dto.response.ValidResponse;
import org.skuhub.skuhub.api.auth.service.AuthService;
import org.skuhub.skuhub.api.auth.service.MailService;
import org.skuhub.skuhub.common.response.BaseResponse;
import org.skuhub.skuhub.common.utills.jwt.dto.JwtDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import jakarta.mail.MessagingException;

import static org.hibernate.query.sqm.tree.SqmNode.log;

@Slf4j
@RestController
@Tag(name = "Auth", description = "Auth API")
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final MailService mailService;
    private final AuthService authService;


    @Operation(summary = "이메일 중복여부 검사", description = "이메일 중복여부 검사 API")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/email/duplicated")
    public BaseResponse<ValidResponse> getEmailDuplicated(@RequestParam String email) {
        return new BaseResponse<>(ValidResponse.builder()
                .valid(authService.getEmailValidity(email))
                .build());
    }

    @Operation(summary = "토큰 재발급", description = "리프레쉬 토큰 기반 액세스 토큰을 재발급 받는 API")
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/reissue")
    public BaseResponse<JwtDto> reissue(@RequestBody ReissueRequest request) {
        return new BaseResponse<>(authService.reissue(request.getRefreshToken()));
    }

    @Operation(summary = "로그인(일반)", description = "일반 로그인 API")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/login")
    public BaseResponse<JwtDto> login(@RequestBody LoginRequest request) {
        BaseResponse<JwtDto> response = new BaseResponse<>(authService.login(request));
        response.setCode("200");
        response.setMessage("로그인 성공");

        return response;
    }


    @Operation(summary = "회원 가입(일반)", description = "일반 회원 가입 API")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    public BaseResponse<JwtDto> signup(@RequestBody SignupRequest request) {
        JwtDto jwtDto = authService.signup(request);
        BaseResponse<JwtDto> response = new BaseResponse<>(jwtDto);
        response.setCode("201");
        response.setMessage("회원가입 성공");

        return response;
    }

}
