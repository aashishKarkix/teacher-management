package com.example.teacher_management.dto;

import com.example.teacher_management.entity.Routine;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoutineDTO {
  private Long routineId;
  private LocalTime startTime;
  private LocalTime endTime;
  private LocalDate routineDate;
  private Long teacherId;
  private Long groupId;

  public static RoutineDTO fromEntity(Routine routine) {
    RoutineDTO dto = new RoutineDTO();
    dto.setRoutineId(routine.getRoutineId());
    dto.setStartTime(routine.getStartTime());
    dto.setEndTime(routine.getEndTime());
    dto.setRoutineDate(routine.getRoutineDate());
    if (routine.getTeacher() != null) {
      dto.setTeacherId(routine.getTeacher().getTeacherId());
    }
    if (routine.getStudyGroup() != null) {
      dto.setGroupId(routine.getStudyGroup().getGroupId());
    }
    return dto;
  }

  public Routine toEntity() {
    Routine routine = new Routine();
    routine.setRoutineId(this.routineId);
    routine.setStartTime(this.startTime);
    routine.setEndTime(this.endTime);
    routine.setRoutineDate(this.routineDate);
    return routine;
  }
}
