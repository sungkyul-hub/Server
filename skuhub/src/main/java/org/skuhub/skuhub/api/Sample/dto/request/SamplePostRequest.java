package org.skuhub.skuhub.api.Sample.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SamplePostRequest {
    private String userId;
    private String email;
    private String password;
    private int year;
    private String department;
    private String name;
}
