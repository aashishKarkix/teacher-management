package com.example.teacher_management.controller;

import com.example.teacher_management.dto.TeacherDTO;
import com.example.teacher_management.exception.IdNotFoundException;
import com.example.teacher_management.exception.TeacherResourceException;
import com.example.teacher_management.service.TeacherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

public class TeacherControllerTest {

  @Mock
  private TeacherService teacherService;

  @InjectMocks
  private TeacherController teacherController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void getTeacherById_validId() {
    Long id = 1L;
    TeacherDTO teacherDto = new TeacherDTO();
    teacherDto.setTeacherId(id);

    when(teacherService.findById(id)).thenReturn(teacherDto);

    ResponseEntity<TeacherDTO> response = teacherController.getTeacherById(id);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(teacherDto, response.getBody());
    verify(teacherService, times(1)).findById(id);
  }

  @Test
  void getTeacherById_invalidId() {
    Long id = 0L;

    IdNotFoundException exception = assertThrows(IdNotFoundException.class,
        () -> teacherController.getTeacherById(id));

    assertEquals("Id is not valid or defined", exception.getMessage());
  }

  @Test
  void createTeacher_validTeacher() {
    TeacherDTO teacherDto = new TeacherDTO();
    TeacherDTO createdTeacherDto = new TeacherDTO();

    when(teacherService.save(teacherDto)).thenReturn(createdTeacherDto);

    ResponseEntity<TeacherDTO> response = teacherController.createTeacher(teacherDto);

    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertEquals(createdTeacherDto, response.getBody());
    verify(teacherService, times(1)).save(teacherDto);
  }

  @Test
  void createTeacher_nullTeacher() {
    TeacherResourceException exception = assertThrows(TeacherResourceException.class,
        () -> teacherController.createTeacher(null));

    assertEquals("Teacher DTO is null", exception.getMessage());
  }

  @Test
  void updateTeacher_validId() {
    Long id = 1L;
    TeacherDTO teacherDto = new TeacherDTO();
    TeacherDTO updatedTeacherDto = new TeacherDTO();

    when(teacherService.update(id, teacherDto)).thenReturn(updatedTeacherDto);

    ResponseEntity<TeacherDTO> response = teacherController.updateTeacher(id, teacherDto);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(updatedTeacherDto, response.getBody());
    verify(teacherService, times(1)).update(id, teacherDto);
  }

  @Test
  void updateTeacher_invalidId() {
    Long id = 0L;
    TeacherDTO teacherDto = new TeacherDTO();

    IdNotFoundException exception = assertThrows(IdNotFoundException.class,
        () -> teacherController.updateTeacher(id, teacherDto));

    assertEquals("Id is not valid or defined", exception.getMessage());
  }

  @Test
  void deleteTeacher_validId() {
    Long id = 1L;

    ResponseEntity<Void> response = teacherController.deleteTeacher(id);

    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    verify(teacherService, times(1)).delete(id);
  }

  @Test
  void deleteTeacher_invalidId() {
    Long id = 0L;

    IdNotFoundException exception = assertThrows(IdNotFoundException.class,
        () -> teacherController.deleteTeacher(id));

    assertEquals("Id is not valid or defined", exception.getMessage());
  }
}
