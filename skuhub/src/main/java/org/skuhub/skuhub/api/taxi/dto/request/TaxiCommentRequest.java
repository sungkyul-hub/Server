package org.skuhub.skuhub.api.taxi.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaxiCommentRequest {

    private int post_id;
    private int comment_id;
    private String name;
    private String comment_content;
}
