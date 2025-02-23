package org.skuhub.skuhub.api.timetable.dto.request;

import lombok.Data;

import java.time.Instant;

@Data
public class UserTimetableRequest {
    private Integer personalKey;
    private Integer userKey;
    private Integer timetableId;
    private String personalGrade;
    private String personalSemester;
    private Instant createdAt;
    private String score;
    private Integer majorStatus;
}
