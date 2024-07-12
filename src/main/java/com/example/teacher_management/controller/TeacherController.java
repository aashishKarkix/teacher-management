package com.example.teacher_management.controller;

import com.example.teacher_management.dto.TeacherDTO;
import com.example.teacher_management.exception.IdNotFoundException;
import com.example.teacher_management.exception.TeacherResourceException;
import com.example.teacher_management.service.TeacherService;
import com.example.teacher_management.util.ValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/teachers")
@Slf4j
public class TeacherController {

  private final TeacherService teacherService;

  public TeacherController(TeacherService teacherService) {
    this.teacherService = teacherService;
  }

  @GetMapping("/{id}")
  public ResponseEntity<TeacherDTO> getTeacherById(@PathVariable Long id) {
    log.info("Fetching teacher with id: {}", id);
    try {
      if (ValidationUtil.isNullOrZero(id)) {
        throw new IdNotFoundException("Id is not valid or defined");
      }
      TeacherDTO teacher = teacherService.findById(id);
      log.info("Successfully fetched teacher: {}", teacher);
      return ResponseEntity.ok(teacher);
    } catch (IdNotFoundException | TeacherResourceException e) {
      throw e;
    } catch (Exception e) {
      log.error("Error fetching teacher by id: {}", e.getMessage());
      throw new TeacherResourceException(
          "Error fetching teacher by id: " + e.getMessage());
    }
  }

  @PostMapping
  public ResponseEntity<TeacherDTO> createTeacher(@RequestBody TeacherDTO teacherDto) {
    log.info("Creating new teacher: {}", teacherDto);
    try {
      if (teacherDto == null) {
        throw new TeacherResourceException("Teacher DTO is null");
      }
      TeacherDTO createdTeacher = teacherService.save(teacherDto);
      log.info("Successfully created teacher: {}", createdTeacher);
      return ResponseEntity.status(HttpStatus.CREATED).body(createdTeacher);
    } catch (TeacherResourceException e) {
      throw e;
    } catch (Exception e) {
      log.error("Error creating teacher: {}", e.getMessage());
      throw new TeacherResourceException("Error creating teacher: " + e.getMessage());
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<TeacherDTO> updateTeacher(@PathVariable Long id,
      @RequestBody TeacherDTO teacherDto) {
    log.info("Updating teacher with id {}: {}", id, teacherDto);
    try {
      if (ValidationUtil.isNullOrZero(id)) {
        throw new IdNotFoundException("Id is not valid or defined");
      }
      TeacherDTO updatedTeacher = teacherService.update(id, teacherDto);
      log.info("Successfully updated teacher: {}", updatedTeacher);
      return ResponseEntity.ok(updatedTeacher);
    } catch (IdNotFoundException | TeacherResourceException e) {
      throw e;
    } catch (Exception e) {
      log.error("Error updating teacher: {}", e.getMessage());
      throw new TeacherResourceException("Error updating teacher: " + e.getMessage());
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteTeacher(@PathVariable Long id) {
    log.info("Deleting teacher with id: {}", id);
    try {
      if (ValidationUtil.isNullOrZero(id)) {
        throw new IdNotFoundException("Id is not valid or defined");
      }
      teacherService.delete(id);
      log.info("Successfully deleted teacher with id: {}", id);
      return ResponseEntity.noContent().build();
    } catch (IdNotFoundException e) {
      throw e;
    } catch (Exception e) {
      log.error("Error deleting teacher: {}", e.getMessage());
      throw new TeacherResourceException("Error deleting teacher: " + e.getMessage());
    }
  }
}
