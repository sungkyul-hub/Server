package org.skuhub.skuhub.model.schedule;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "NOTICE_TB")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id", nullable = false)
    private Integer id;

    @Size(max = 20)
    @NotNull
    @Column(name = "notice_category", nullable = false, length = 20)
    private String noticeCategory;

    @Size(max = 50)
    @NotNull
    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @NotNull
    @Column(name = "notice_date", nullable = false)
    private LocalDate noticeDate;

    @NotNull
    @Column(name = "notice_modify_date", nullable = false)
    private LocalDate noticeModifyDate;

    @Size(max = 10)
    @NotNull
    @Column(name = "writer", nullable = false, length = 10)
    private String writer;

    @NotNull
    @Lob
    @Column(name = "notice_original_content", nullable = false)
    private String noticeOriginalContent;

    @Size(max = 10000)
    @NotNull
    @Column(name = "notice_content", nullable = false, length = 10000)
    private String noticeContent;

    @Size(max = 500)
    @NotNull
    @Column(name = "url", nullable = false, length = 500)
    private String url;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }

    public void setNoticeOriginalContent(String noticeOriginalContent) {
        this.noticeOriginalContent = noticeOriginalContent;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public void setNoticeModifyDate(LocalDate noticeModifyDate) {
        this.noticeModifyDate = noticeModifyDate;
    }

    public void setNoticeDate(LocalDate noticeDate) {
        this.noticeDate = noticeDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setNoticeCategory(String noticeCategory) {
        this.noticeCategory = noticeCategory;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
