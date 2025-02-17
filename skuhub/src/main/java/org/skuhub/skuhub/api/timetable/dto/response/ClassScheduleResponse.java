package org.skuhub.skuhub.dto.timetable.response;

import java.time.ZonedDateTime;

/**
 * 강의 시간표 응답 DTO
 */
public class ClassScheduleResponse {
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

    public ClassScheduleResponse(Long timetableId, int year, String semester, String department,
                                 int grade, String completionType, String subjectCode, int classNumber,
                                 String subjectName, int credit, String professorName, String classTime,
                                 String classroom, String generalArea, ZonedDateTime createdAt) {
        this.timetableId = timetableId;
        this.year = year;
        this.semester = semester;
        this.department = department;
        this.grade = grade;
        this.completionType = completionType;
        this.subjectCode = subjectCode;
        this.classNumber = classNumber;
        this.subjectName = subjectName;
        this.credit = credit;
        this.professorName = professorName;
        this.classTime = classTime;
        this.classroom = classroom;
        this.generalArea = generalArea;
        this.createdAt = createdAt;
    }

    // Getters (생략 가능)
}