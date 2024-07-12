package com.example.teacher_management.controller;


import com.example.teacher_management.dto.StudyGroupDTO;
import com.example.teacher_management.entity.StudyGroup;
import com.example.teacher_management.exception.GroupResourceException;
import com.example.teacher_management.exception.IdNotFoundException;
import com.example.teacher_management.service.StudyGroupService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StudyGroupControllerTest {

  @Mock
  private StudyGroupService studyGroupService;

  @InjectMocks
  private StudyGroupController studyGroupController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void getStudyGroupById_validId() {
    Long id = 1L;
    StudyGroup group = new StudyGroup();
    group.setGroupId(id);

    when(studyGroupService.findById(id)).thenReturn(group);

    ResponseEntity<StudyGroup> response = studyGroupController.getStudyGroupById(id);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(group, response.getBody());
    verify(studyGroupService, times(1)).findById(id);
  }

  @Test
  void getStudyGroupById_invalidId() {
    Long id = 0L;

    IdNotFoundException exception = assertThrows(IdNotFoundException.class,
        () -> studyGroupController.getStudyGroupById(id));

    assertEquals("Id is not valid or Defined", exception.getMessage());
  }

  @Test
  void createStudyGroup_validGroup() {
    StudyGroupDTO groupDto = new StudyGroupDTO();
    StudyGroupDTO createdGroupDto = new StudyGroupDTO();

    when(studyGroupService.save(groupDto)).thenReturn(createdGroupDto);

    ResponseEntity<StudyGroupDTO> response = studyGroupController.createStudyGroup(groupDto);

    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertEquals(createdGroupDto, response.getBody());
    verify(studyGroupService, times(1)).save(groupDto);
  }

  @Test
  void createStudyGroup_nullGroup() {
    GroupResourceException exception = assertThrows(GroupResourceException.class,
        () -> studyGroupController.createStudyGroup(null));

    assertEquals("Group is not found", exception.getMessage());
  }

  @Test
  void updateStudyGroup_validId() {
    Long id = 1L;
    StudyGroupDTO groupDto = new StudyGroupDTO();
    StudyGroupDTO updatedGroupDto = new StudyGroupDTO();

    when(studyGroupService.update(id, groupDto)).thenReturn(updatedGroupDto);

    ResponseEntity<StudyGroupDTO> response = studyGroupController.updateStudyGroup(id, groupDto);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(updatedGroupDto, response.getBody());
    verify(studyGroupService, times(1)).update(id, groupDto);
  }

  @Test
  void updateStudyGroup_invalidId() {
    Long id = 0L;
    StudyGroupDTO groupDto = new StudyGroupDTO();

    IdNotFoundException exception = assertThrows(IdNotFoundException.class,
        () -> studyGroupController.updateStudyGroup(id, groupDto));

    assertEquals("Id is not valid or Defined", exception.getMessage());
  }

  @Test
  void deleteStudyGroup_validId() {
    Long id = 1L;

    ResponseEntity<Void> response = studyGroupController.deleteStudyGroup(id);

    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    verify(studyGroupService, times(1)).delete(id);
  }

  @Test
  void deleteStudyGroup_invalidId() {
    Long id = 0L;

    IdNotFoundException exception = assertThrows(IdNotFoundException.class,
        () -> studyGroupController.deleteStudyGroup(id));

    assertEquals("Id was not specified or valid", exception.getMessage());
  }
}

