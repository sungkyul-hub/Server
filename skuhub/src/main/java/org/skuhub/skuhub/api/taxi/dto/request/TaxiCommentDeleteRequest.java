package org.skuhub.skuhub.api.taxi.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class TaxiCommentDeleteRequest {
    private int postId;
    private int commentId;
}
