package org.skuhub.skuhub.api.taxi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@AllArgsConstructor
public class TaxiPostResponse {

    private int postId;
    private String title;
    private String description;
    private OffsetDateTime rideTime;
    private OffsetDateTime createdAt;
    private String name;
    private int status;
    private int numberOfPeople;

}
