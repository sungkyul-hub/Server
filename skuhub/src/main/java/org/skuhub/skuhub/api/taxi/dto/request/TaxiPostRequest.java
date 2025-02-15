package org.skuhub.skuhub.api.taxi.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class TaxiPostRequest {

    private String title;
    private String departure_location;
    private int head_count;
    private String ride_time;
    private String description;
    private OffsetDateTime created_at;
    private String name;
}
