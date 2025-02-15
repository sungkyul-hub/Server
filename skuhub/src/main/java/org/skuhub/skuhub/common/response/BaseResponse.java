package org.skuhub.skuhub.common.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Builder
@AllArgsConstructor
public class BaseResponse<T> {
    @Schema(description = "성공 여부", example = "true")
    @Builder.Default
    private boolean isSuccess = true;

    @Schema(description = "응답 코드", example = "200")
    @Builder.Default
    private String code = "200";

    @Schema(description = "응답 메세지", example = "SUCCESS")
    @Builder.Default
    private String message = "SUCCESS";

    @Schema(description = "응답 일시", type = "string", format = "date-time", example = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private OffsetDateTime responseAt;

    @Schema(description = "응답 데이터")
    private T data;

    // 기본 생성자에서 응답 시간을 현재 시간으로 초기화
    public BaseResponse() {
        this.responseAt = OffsetDateTime.now();
    }

    // 데이터만 받아서 BaseResponse를 생성하는 방식
    public BaseResponse(T data) {
        this();
        this.data = data;
    }

    // statusCode와 message를 받아서 응답을 생성하는 생성자 추가
    public BaseResponse(int statusCode, String message) {
        this();
        this.isSuccess = statusCode == 200;  // 응답 코드가 200이면 성공으로 처리
        this.code = String.valueOf(statusCode);
        this.message = message;
    }
}
