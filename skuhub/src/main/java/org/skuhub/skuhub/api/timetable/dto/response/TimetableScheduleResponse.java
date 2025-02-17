package org.skuhub.skuhub.api.timetable.dto.response;

import lombok.*;
import java.time.Instant;
import org.skuhub.skuhub.model.timetable.TimetableScheduleEntity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TimetableScheduleResponse {

    private Integer id;
    private Integer year;
    private Integer semester;
    private String department;
    private Integer grade;
    private String completionType;
    private Integer subjectCode;
    private Integer classNumber;
    private String subjectName;
    private Integer credit;
    private String professorName;
    private String classTime;
    private String classroom;
    private String generalArea;
    private Instant createdAt;

    public static TimetableScheduleResponse fromEntity(TimetableScheduleEntity entity) {
        return TimetableScheduleResponse.builder()
                .id(entity.getId())
                .year(entity.getYear())
                .semester(entity.getSemester())
                .department(entity.getDepartment())
                .grade(entity.getGrade())
                .completionType(entity.getCompletionType())
                .subjectCode(entity.getSubjectCode())
                .classNumber(entity.getClassNumber())
                .subjectName(entity.getSubjectName())
                .credit(entity.getCredit())
                .professorName(entity.getProfessorName())
                .classTime(entity.getClassTime())
                .classroom(entity.getClassroom())
                .generalArea(entity.getGeneralArea())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
