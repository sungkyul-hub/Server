package org.skuhub.skuhub.api.notice.dto.response;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@AllArgsConstructor
public class NoticeDetailsResponse {
    private Long noticeId;
    private String category;
    private String title;
    private OffsetDateTime noticeModifyDate;
    private String writer;
    private String content;
    private byte[] noticeOriginalContent;
    private String url;
}
