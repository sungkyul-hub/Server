package org.skuhub.skuhub.api.timetable.dto.response;

import java.time.ZonedDateTime;

/**
 * 강의 시간표 응답 DTO
 */
public class TimetableScheduleResponse {
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
    private ZonedDateTime createdAt;

    public TimetableScheduleResponse() {

    }

    // Getters (생략 가능)
}