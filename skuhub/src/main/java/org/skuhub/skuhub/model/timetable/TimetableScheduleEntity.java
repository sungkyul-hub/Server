package org.skuhub.skuhub.model.timetable;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.Instant;
import java.time.ZonedDateTime;

@Entity
@Table(name = "schedule_tb")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimetableScheduleEntity {

}