package com.example.teacher_management.service.impl;

import com.example.teacher_management.dto.RoutineDTO;
import com.example.teacher_management.entity.StudyGroup;
import com.example.teacher_management.entity.Routine;
import com.example.teacher_management.entity.Teacher;
import com.example.teacher_management.exception.GroupResourceException;
import com.example.teacher_management.exception.RoutineResourceException;
import com.example.teacher_management.exception.TeacherResourceException;
import com.example.teacher_management.repository.GroupRepository;
import com.example.teacher_management.repository.RoutineRepository;
import com.example.teacher_management.repository.TeacherRepository;
import com.example.teacher_management.service.RoutineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class RoutineServiceImpl implements RoutineService {

  private final RoutineRepository routineRepository;
  private final TeacherRepository teacherRepository;
  private final GroupRepository groupRepository;

  public RoutineServiceImpl(RoutineRepository routineRepository,
      TeacherRepository teacherRepository, GroupRepository groupRepository) {
    this.routineRepository = routineRepository;
    this.teacherRepository = teacherRepository;
    this.groupRepository = groupRepository;
  }

  @Override
  public RoutineDTO saveRoutine(RoutineDTO routineDto) {
    log.info("Saving routine: {}", routineDto);
    try {
      Routine routine = routineDto.toEntity();

      Long teacherId = routineDto.getTeacherId();
      Optional<Teacher> optionalTeacher = teacherRepository.findById(teacherId);
      optionalTeacher.ifPresent(routine::setTeacher);

      Long groupId = routineDto.getGroupId();
      Optional<StudyGroup> optionalGroup = groupRepository.findById(groupId);
      optionalGroup.ifPresent(routine::setStudyGroup);

      Routine savedRoutine = routineRepository.save(routine);
      log.info("Saved routine: {}", savedRoutine);

      return RoutineDTO.fromEntity(savedRoutine);
    } catch (Exception e) {
      log.error("Error while saving routine: {}", e.getMessage());
      throw new RoutineResourceException("Error while saving routine: " + e.getMessage());
    }
  }

  @Override
  public RoutineDTO updateRoutine(Long id, RoutineDTO routineDto) {
    log.info("Updating routine with id {}: {}", id, routineDto);
    try {
      Routine existingRoutine = routineRepository.findById(id).orElseThrow(
          () -> new RoutineResourceException("Routine not found with id: " + id));

      existingRoutine.setRoutineDate(routineDto.getRoutineDate());
      existingRoutine.setStartTime(routineDto.getStartTime());
      existingRoutine.setEndTime(routineDto.getEndTime());

      Long teacherId = routineDto.getTeacherId();
      Optional<Teacher> optionalTeacher = teacherRepository.findById(teacherId);
      optionalTeacher.ifPresent(existingRoutine::setTeacher);

      Long groupId = routineDto.getGroupId();
      Optional<StudyGroup> optionalGroup = groupRepository.findById(groupId);
      optionalGroup.ifPresent(existingRoutine::setStudyGroup);

      Routine updatedRoutine = routineRepository.save(existingRoutine);
      log.info("Updated routine: {}", updatedRoutine);

      return RoutineDTO.fromEntity(updatedRoutine);
    } catch (Exception e) {
      log.error("Error updating routine with id {}: {}", id, e.getMessage());
      throw new RoutineResourceException(
          "Error updating routine with id " + id + ": " + e.getMessage());
    }
  }

  @Override
  public void deleteRoutine(Long id) {
    log.info("Deleting routine with id: {}", id);
    try {
      if (!routineRepository.existsById(id)) {
        throw new RoutineResourceException("Routine not found with id: " + id);
      }
      routineRepository.deleteById(id);
      log.info("Deleted routine with id: {}", id);
    } catch (Exception e) {
      log.error("Error deleting routine with id {}: {}", id, e.getMessage());
      throw new RoutineResourceException(
          "Error deleting routine with id " + id + ": " + e.getMessage());
    }
  }

  @Override
  public List<RoutineDTO> getAllRoutines() {
    log.info("Fetching all routines");
    try {
      List<Routine> routines = routineRepository.findAll();
      log.info("Fetched {} routines", routines.size());

      return routines.stream().map(RoutineDTO::fromEntity).toList();
    } catch (Exception e) {
      log.error("Error fetching all routines: {}", e.getMessage());
      throw new RoutineResourceException(
          "Error fetching all routines: " + e.getMessage());
    }
  }

  @Override
  public Optional<RoutineDTO> getRoutineById(Long id) {
    log.info("Fetching routine with id: {}", id);
    try {
      Routine routine = routineRepository.findById(id).orElseThrow(
          () -> new RoutineResourceException("Routine not found with id: " + id));
      log.info("Fetched routine: {}", routine);
      return Optional.of(RoutineDTO.fromEntity(routine));
    } catch (Exception e) {
      log.error("Error fetching routine with id {}: {}", id, e.getMessage());
      throw new RoutineResourceException(
          "Error fetching routine with id " + id + ": " + e.getMessage());
    }
  }

  @Override
  public long calculateTeacherWorkload(String teacherName, LocalDate startDate,
      LocalDate endDate) {
    log.info("Calculating workload for teacher '{}' from {} to {}", teacherName,
        startDate, endDate);
    Teacher teacher = teacherRepository.findByName(teacherName).orElseThrow(
        () -> new TeacherResourceException(
            "Teacher not found with name: " + teacherName));

    try {
      List<Routine> routines = routineRepository.findByTeacherAndRoutineDateBetween(
          teacher, startDate, endDate);
      long workload = routines.stream().mapToLong(
          routine -> Duration.between(routine.getStartTime(), routine.getEndTime())
              .toHours()).sum();
      log.info("Calculated Teacher workload: {} hours", workload);
      return workload;
    } catch (Exception e) {
      log.error("Error while calculating teacher workload: {}", e.getMessage());
      throw new RoutineResourceException(
          "Error while calculating teacher workload: " + e.getMessage());
    }
  }

  @Override
  public long calculateGroupWorkload(Long groupId) {
    log.info("Calculating workload for group with id: {}", groupId);
    StudyGroup studyGroup = groupRepository.findById(groupId).orElseThrow(
        () -> new GroupResourceException("Group not found with id: " + groupId));

    try {
      List<Routine> routines = routineRepository.findByStudyGroup(studyGroup);
      long workload = routines.stream().mapToLong(
          routine -> Duration.between(routine.getStartTime(), routine.getEndTime())
              .toHours()).sum();
      log.info("Calculated workload: {} hours", workload);
      return workload;
    } catch (Exception e) {
      log.error("Error while calculating group workload: {}", e.getMessage());
      throw new RoutineResourceException(
          "Error while calculating group workload: " + e.getMessage());
    }
  }
}
