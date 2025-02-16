package org.skuhub.skuhub.api.taxi.dto.response;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.skuhub.skuhub.api.taxi.dto.request.TaxiCommentRequest;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class TaxiPostDetailsResponse {
    private int postId;
    private String title;
    private String description;
    private OffsetDateTime rideTime;
    private OffsetDateTime createdAt;
    private String name;
    private int status;
    private int numberOfPeople;
    private List<TaxiCommentRequest> comments;
}
