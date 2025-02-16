package org.skuhub.skuhub.api.taxi.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class TaxiPostRequest {
    @NotNull // 필수 입력값
    private String userId;
    private String title;
    private String departureLocation;
    private int numberOfPeople;
    private OffsetDateTime rideTime;
    private String description;

}
