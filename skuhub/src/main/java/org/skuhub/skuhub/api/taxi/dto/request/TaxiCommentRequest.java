package org.skuhub.skuhub.api.taxi.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaxiCommentRequest {

    private int postId;
    private int commentId;
    private String name;
    private String commentIontent;
}
