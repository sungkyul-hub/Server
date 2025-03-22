package org.skuhub.skuhub.api.timetable.dto.response;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompletionResponse {
    private Integer userKey;  // Integer 타입으로 저장
    private Instant updatedAt;
}

