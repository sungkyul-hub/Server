package org.skuhub.skuhub.api.Sample.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@AllArgsConstructor
public class SampleResponse {
    private int userKey;
    private String userId;
    private String email;
    private int year;
    private String department;
    private String name;
    private OffsetDateTime createdAt;
}

