package com.example.teacher_management.repository;

import com.example.teacher_management.entity.StudyGroup;
import com.example.teacher_management.entity.Routine;
import com.example.teacher_management.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface RoutineRepository extends JpaRepository<Routine, Long> {
  List<Routine> findByTeacherAndRoutineDateBetween(Teacher teacher, LocalDate startDate, LocalDate endDate);
  List<Routine> findByStudyGroup(StudyGroup studyGroup);
}
