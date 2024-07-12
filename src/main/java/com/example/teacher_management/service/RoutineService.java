package com.example.teacher_management.service;

import com.example.teacher_management.dto.RoutineDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RoutineService {
  RoutineDTO saveRoutine(RoutineDTO routine);

  long calculateTeacherWorkload(String teacherName, LocalDate startDate,
      LocalDate endDate);

  long calculateGroupWorkload(Long groupId);

  RoutineDTO updateRoutine(Long id, RoutineDTO routineDTO);

  List<RoutineDTO> getAllRoutines();

  Optional<RoutineDTO> getRoutineById(Long id);

  void deleteRoutine(Long id);
}
