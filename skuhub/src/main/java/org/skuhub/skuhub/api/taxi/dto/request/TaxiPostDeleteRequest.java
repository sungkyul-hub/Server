package org.skuhub.skuhub.api.taxi.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class TaxiPostDeleteRequest {

    private int post_id;
}
