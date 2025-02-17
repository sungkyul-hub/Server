package org.skuhub.skuhub.api.taxi.dto.response;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Getter
@Setter
@AllArgsConstructor
public class TaxiPostResponse {

    private Long postId;
    private String name;
    private String title;
    private String departureLocation;
    private String description;
    private OffsetDateTime rideTime;
    private int headCount;
    private int numberOfPeople;
    private OffsetDateTime createdAt;

    public TaxiPostResponse() {

    }
}
