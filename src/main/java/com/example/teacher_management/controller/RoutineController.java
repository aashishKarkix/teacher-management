package com.example.teacher_management.controller;

import com.example.teacher_management.dto.RoutineDTO;
import com.example.teacher_management.exception.IdNotFoundException;
import com.example.teacher_management.exception.RoutineResourceException;
import com.example.teacher_management.exception.TeacherResourceException;
import com.example.teacher_management.service.RoutineService;
import com.example.teacher_management.util.ValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@Slf4j
public class RoutineController {

  private final RoutineService routineService;

  public RoutineController(RoutineService routineService) {
    this.routineService = routineService;
  }

  @PostMapping("/routine")
  public ResponseEntity<RoutineDTO> createRoutine(@RequestBody RoutineDTO routine) {
    log.info("Creating new routine: {}", routine);
    try {
      if (routine == null) {
        throw new RoutineResourceException("Routine resource was not found");
      }
      RoutineDTO createdRoutine = routineService.saveRoutine(routine);
      log.info("Successfully created routine: {}", createdRoutine);
      return ResponseEntity.status(HttpStatus.CREATED).body(createdRoutine);
    } catch (Exception e) {
      log.error("Error creating routine: {}", e.getMessage());
      throw new RoutineResourceException("Error creating routine: " + e.getMessage());
    }
  }

  @GetMapping("/routines")
  public List<RoutineDTO> getAllRoutines() {
    log.info("Fetching all routines");
    try {
      List<RoutineDTO> routines = routineService.getAllRoutines();
      log.info("Fetched {} routines", routines.size());
      return routines;
    } catch (Exception e) {
      log.error("Error fetching all routines: {}", e.getMessage());
      throw new RoutineResourceException(
          "Error fetching all routines: " + e.getMessage());
    }
  }

  @GetMapping("/routine/{id}")
  public ResponseEntity<RoutineDTO> getRoutineById(@PathVariable Long id) {
    log.info("Fetching routine with id: {}", id);
    try {
      Optional<RoutineDTO> routine = routineService.getRoutineById(id);
      if (routine.isEmpty()) {
        throw new IdNotFoundException("Routine not found with id: " + id);
      }
      log.info("Successfully fetched routine: {}", routine.get());
      return ResponseEntity.ok(routine.get());
    } catch (IdNotFoundException | RoutineResourceException e) {
      throw e;
    } catch (Exception e) {
      log.error("Error fetching routine by id: {}", e.getMessage());
      throw new RoutineResourceException(
          "Error fetching routine by id: " + e.getMessage());
    }
  }

  @PutMapping("/routine/{id}")
  public ResponseEntity<RoutineDTO> updateRoutine(@PathVariable Long id,
      @RequestBody RoutineDTO routine) {
    log.info("Updating routine with id {}: {}", id, routine);
    try {
      if (ValidationUtil.isNullOrZero(id)) {
        throw new IdNotFoundException("Id is not valid or Defined");
      }
      RoutineDTO updatedRoutine = routineService.updateRoutine(id, routine);
      log.info("Successfully updated routine: {}", updatedRoutine);
      return ResponseEntity.ok(updatedRoutine);
    } catch (IdNotFoundException | RoutineResourceException e) {
      throw e;
    } catch (Exception e) {
      log.error("Error updating routine: {}", e.getMessage());
      throw new RoutineResourceException("Error updating routine: " + e.getMessage());
    }
  }

  @DeleteMapping("/routine/{id}")
  public ResponseEntity<Void> deleteRoutine(@PathVariable Long id) {
    log.info("Deleting routine with id: {}", id);
    try {
      if (ValidationUtil.isNullOrZero(id)) {
        throw new IdNotFoundException("Id is not valid or Defined");
      }
      routineService.deleteRoutine(id);
      log.info("Successfully deleted routine with id: {}", id);
      return ResponseEntity.noContent().build();
    } catch (IdNotFoundException e) {
      throw e;
    } catch (Exception e) {
      log.error("Error deleting routine: {}", e.getMessage());
      throw new RoutineResourceException("Error deleting routine: " + e.getMessage());
    }
  }

  @GetMapping("/teacher/workload")
  public long getTeacherWorkload(@RequestParam String teacherName,
      @RequestParam String startDate, @RequestParam String endDate) {
    log.info(">> Inside teacher workload controller <<");
    try {
      if (ValidationUtil.isEmptyOrNull(teacherName) || ValidationUtil.isEmptyOrNull(
          startDate) || ValidationUtil.isEmptyOrNull(endDate)) {
        log.warn("Provided teacher resources are null or empty");
        throw new TeacherResourceException(
            "Provided teacher resources are null or empty");
      }
      LocalDate start = LocalDate.parse(startDate);
      LocalDate end = LocalDate.parse(endDate);
      long workLoadTime = routineService.calculateTeacherWorkload(teacherName, start,
          end);
      log.info("Workload calculated successfully: {} hrs", workLoadTime);
      return workLoadTime;
    } catch (Exception e) {
      log.warn("Failed to calculate the workload: {}", e.getMessage());
      throw new TeacherResourceException(
          "Error calculating teacher workload: " + e.getMessage());
    }
  }

  @GetMapping("/group/workload")
  public long getGroupWorkload(@RequestParam Long groupId) {
    log.info(">> Inside group workload Controller <<");
    try {
      if (ValidationUtil.isNullOrZero(groupId)) {
        throw new IdNotFoundException("Id is missing or Empty: " + groupId);
      }
      return routineService.calculateGroupWorkload(groupId);
    } catch (Exception e) {
      log.error("Error calculating group workload: {}", e.getMessage());
      throw new RoutineResourceException(
          "Error calculating group workload: " + e.getMessage());
    }
  }
}
