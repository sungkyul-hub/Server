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
    private int post_id;
    private String title;
    private String description;
    private OffsetDateTime ride_time;
    private OffsetDateTime created_at;
    private String name;
    private int status;
    private int number_of_people;
    private List<TaxiCommentRequest> comments;
}
