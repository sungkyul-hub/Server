package org.skuhub.skuhub.model.timetable;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "PERSONAL_TIMETABLE_TB") // 개인 시간표 테이블
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonalTimetableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "personal_key", nullable = false)
    private Integer personalKey;

    @NotNull
    @Column(name = "user_key", nullable = false)
    private Integer userKey;

    @NotNull
    @Column(name = "timetable_id", nullable = false)
    private Integer timetableId;

    @NotNull
    @Column(name = "personal_grade", nullable = false, length = 10)
    private String personalGrade;

    @NotNull
    @Column(name = "personal_semester", nullable = false, length = 10)
    private String personalSemester;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "score", length = 5)
    private String score;

    @Column(name = "major_status")
    private Integer majorStatus;
}
