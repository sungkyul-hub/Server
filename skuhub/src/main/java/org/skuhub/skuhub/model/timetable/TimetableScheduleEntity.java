package org.skuhub.skuhub.model.timetable;

import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;

@Entity
@Table(name = "schedule_tb")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimetableScheduleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long timetableId;

    private int year;
    private String semester;
    private String department;
    private int grade;
    private String completionType;
    private String subjectCode;
    private int classNumber;
    private String subjectName;
    private int credit;
    private String professorName;
    private String classTime;
    private String classroom;
    private String generalArea;

    @Column(nullable = false, updatable = false)
    private ZonedDateTime createdAt = ZonedDateTime.now();
}