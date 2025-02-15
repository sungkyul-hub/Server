package org.skuhub.skuhub.api.taxi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@AllArgsConstructor
public class TaxiPostResponse {

    private int post_id;
    private String title;
    private String description;
    private OffsetDateTime ride_time;
    private OffsetDateTime created_at;
    private String name;
    private int status;
    private int number_of_people;

}
