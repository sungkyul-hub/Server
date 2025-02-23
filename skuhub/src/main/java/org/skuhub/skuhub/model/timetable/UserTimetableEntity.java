package org.skuhub.skuhub.model.timetable;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "PERSONAL_TIMETABLE_TB")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserTimetableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "personal_key", nullable = false)
    private Integer personalKey;

    @Column(name = "user_key", nullable = false)
    private Integer userKey;

    @Column(name = "timetable_id", nullable = false)
    private Integer timetableId;

    @Column(name = "personal_grade", nullable = false)
    private String personalGrade;

    @Column(name = "personal_semester", nullable = false)
    private String personalSemester;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "score")
    private String score;

    @Column(name = "major_status")
    private Integer majorStatus;
}
