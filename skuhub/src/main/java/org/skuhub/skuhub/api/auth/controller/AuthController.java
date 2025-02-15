package org.skuhub.skuhub.api.auth.controller;

import groovy.util.logging.Slf4j;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.skuhub.skuhub.api.auth.service.MailService;
import org.skuhub.skuhub.common.response.BaseResponse;
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

    @Operation(summary = "이메일 인증번호 발송", description = "이메일 인증번호를 발송하는 API")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/send-email-verification")
    public BaseResponse<String> sendVerificationCode(@RequestParam String email) {
        // 이메일 도메인 확인
        if (!email.endsWith("@sungkyul.ac.kr")) {
            return new BaseResponse<>(HttpStatus.BAD_REQUEST.value(), "유효하지 않은 이메일 도메인입니다.");
        }

        try {
            // 이메일 인증 코드 생성 및 발송
            String authCode = mailService.sendSimpleMessage(email);
            // 응답 반환
            return new BaseResponse<>(authCode);
        } catch (MessagingException e) {
            log.error("메일 발송 오류: {}", e.getMessage(), e);
            return new BaseResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "메일 발송 실패");
        }
    }
}
