package org.skuhub.skuhub.api.schedule.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.skuhub.skuhub.model.schedule.ScheduleEntity;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class ScheduleResponse {
    private Integer noticeId;
    private String noticeCategory;
    private String title;
    private LocalDate noticeDate;
    private String writer;
    private String noticeContent;
    private String url;
    private Instant createdAt;
    private Instant updatedAt;

    public static ScheduleResponse fromEntity(ScheduleEntity entity) {
        return new ScheduleResponse(
                entity.getId(),
                entity.getNoticeCategory(),
                entity.getTitle(),
                entity.getNoticeDate(),
                entity.getWriter(),
                entity.getNoticeContent(),
                entity.getUrl(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
