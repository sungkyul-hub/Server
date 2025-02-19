package org.skuhub.skuhub.model.notice;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.skuhub.skuhub.model.user.UserInfoJpaEntity;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "NOTICE_TB")
public class NoticeJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id", nullable = false)
    private Integer id;

    @Size(max = 10)
    @NotNull
    @Column(name = "notice_type", nullable = false, length = 10)
    private String noticeType;

    @Size(max = 50)
    @NotNull
    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @NotNull
    @Column(name = "notice_date", nullable = false)
    private Instant noticeDate;

    @Size(max = 10)
    @NotNull
    @Column(name = "author", nullable = false, length = 10)
    private String author;

    @Size(max = 10000)
    @NotNull
    @Column(name = "notice_original_content", nullable = false, length = 10000)
    private String noticeOriginalContent;

    @Size(max = 1000)
    @NotNull
    @Column(name = "notice_content", nullable = false, length = 1000)
    private String noticeContent;

    @Size(max = 500)
    @NotNull
    @Column(name = "url", nullable = false, length = 500)
    private String url;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @OneToMany(mappedBy = "notice")
    private Set<NotificationHistory> notificationHistories = new LinkedHashSet<>();

}
