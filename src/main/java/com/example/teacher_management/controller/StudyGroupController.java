package com.example.teacher_management.controller;

import com.example.teacher_management.dto.StudyGroupDTO;
import com.example.teacher_management.entity.StudyGroup;
import com.example.teacher_management.exception.GroupResourceException;
import com.example.teacher_management.exception.IdNotFoundException;
import com.example.teacher_management.service.StudyGroupService;
import com.example.teacher_management.util.ValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/groups")
@Slf4j
public class StudyGroupController {

  private final StudyGroupService studyGroupService;

  public StudyGroupController(StudyGroupService studyGroupService) {
    this.studyGroupService = studyGroupService;
  }

  @GetMapping("/{id}")
  public ResponseEntity<StudyGroup> getStudyGroupById(@PathVariable Long id) {
    log.info("Fetching study group with id: {}", id);
    try {
      if (ValidationUtil.isNullOrZero(id)) {
        throw new IdNotFoundException("Id is not valid or Defined");
      }
      StudyGroup group = studyGroupService.findById(id);
      log.info("Successfully fetched study group: {}", group);
      return ResponseEntity.ok(group);
    } catch (IdNotFoundException | GroupResourceException e) {
      throw e;
    } catch (Exception e) {
      log.error("Error fetching group by id: {}", e.getMessage());
      throw new GroupResourceException("Error fetching group by id: " + e.getMessage());
    }
  }

  @PostMapping
  public ResponseEntity<StudyGroupDTO> createStudyGroup(@RequestBody StudyGroupDTO group) {
    log.info("Creating new study group: {}", group);
    try {
      if (group == null) {
        throw new GroupResourceException("Group is not found");
      }
      StudyGroupDTO createdGroup = studyGroupService.save(group);
      log.info("Successfully created study group: {}", createdGroup);
      return ResponseEntity.status(HttpStatus.CREATED).body(createdGroup);
    } catch (GroupResourceException e) {
      throw e;
    } catch (Exception e) {
      log.error("Error creating group: {}", e.getMessage());
      throw new GroupResourceException("Error creating group: " + e.getMessage());
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<StudyGroupDTO> updateStudyGroup(@PathVariable Long id,
      @RequestBody StudyGroupDTO group) {
    log.info("Updating study group with id {}: {}", id, group);
    try {
      if (ValidationUtil.isNullOrZero(id)) {
        throw new IdNotFoundException("Id is not valid or Defined");
      }
      StudyGroupDTO updatedGroup = studyGroupService.update(id, group);
      log.info("Successfully updated study group: {}", updatedGroup);
      return ResponseEntity.ok(updatedGroup);
    } catch (IdNotFoundException | GroupResourceException e) {
      throw e;
    } catch (Exception e) {
      log.error("Error updating group: {}", e.getMessage());
      throw new GroupResourceException("Error updating group: " + e.getMessage());
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteStudyGroup(@PathVariable Long id) {
    log.info("Deleting study group with id: {}", id);
    try {
      if (ValidationUtil.isNullOrZero(id)) {
        throw new IdNotFoundException("Id was not specified or valid");
      }
      studyGroupService.delete(id);
      log.info("Successfully deleted study group with id: {}", id);
      return ResponseEntity.noContent().build();
    } catch (IdNotFoundException e) {
      throw e;
    } catch (Exception e) {
      log.error("Error deleting group: {}", e.getMessage());
      throw new GroupResourceException("Error deleting group: " + e.getMessage());
    }
  }
}
