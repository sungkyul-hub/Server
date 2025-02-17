package org.skuhub.skuhub.api.timetable.dto.request;

import java.time.ZonedDateTime;

/**
 * 강의 시간표 요청 DTO
 */
public class TimetableScheduleRequest {
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

    // 생성자, Getter, Setter (필요시 추가)
}