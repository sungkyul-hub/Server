package org.skuhub.skuhub.api.Sample.dto.response;

import lombok.Getter;
import lombok.Setter;
import java.time.OffsetDateTime;

@Getter
@Setter
public class SampleResponse {
    private Long id;
    private String text;
    private boolean yn;
    private OffsetDateTime createdDate;

    public SampleResponse(Long id, String text, boolean yn, OffsetDateTime createdDate) {
        this.id = id;
        this.text = text;
        this.yn = yn;
        this.createdDate = createdDate;
    }
}
