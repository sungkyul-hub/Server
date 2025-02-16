package org.skuhub.skuhub.api.taxi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@AllArgsConstructor
public class TaxiCommentResponse {

    private String name;
    private String commentContent;
    private OffsetDateTime createdAt;
}
