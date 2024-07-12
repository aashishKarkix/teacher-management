package com.example.teacher_management.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import com.example.teacher_management.dto.TeacherDTO;
import com.example.teacher_management.entity.Teacher;
import com.example.teacher_management.exception.TeacherResourceException;
import com.example.teacher_management.repository.TeacherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TeacherServiceImplTest {

  @Mock
  private TeacherRepository teacherRepository;

  @InjectMocks
  private TeacherServiceImpl teacherService;

  private Teacher teacher;
  private TeacherDTO teacherDTO;

  @BeforeEach
  void setUp() {
    teacher = new Teacher();
    teacher.setTeacherId(1L);
    teacher.setName("Aashish Karki");
    teacher.setDepartment("IT");
    teacher.setEmail("aashish@gmail.com");

    teacherDTO = new TeacherDTO();
    teacherDTO.setTeacherId(1L);
    teacherDTO.setName("Aashish Karki");
    teacherDTO.setDepartment("IT");
    teacherDTO.setEmail("aashish@gmail.com");
  }

  @Test
  void findById_success() {
    when(teacherRepository.findById(anyLong())).thenReturn(Optional.of(teacher));

    TeacherDTO foundTeacher = teacherService.findById(1L);

    assertNotNull(foundTeacher);
    assertEquals(teacher.getTeacherId(), foundTeacher.getTeacherId());
    verify(teacherRepository, times(1)).findById(anyLong());
  }

  @Test
  void findById_notFound() {
    when(teacherRepository.findById(anyLong())).thenReturn(Optional.empty());

    TeacherResourceException exception = assertThrows(TeacherResourceException.class,
        () -> teacherService.findById(1L));

    assertEquals("Teacher not found with id: 1", exception.getMessage());
  }

  @Test
  void save_success() {
    when(teacherRepository.save(any(Teacher.class))).thenReturn(teacher);

    TeacherDTO savedTeacherDTO = teacherService.save(teacherDTO);

    assertNotNull(savedTeacherDTO);
    assertEquals(teacherDTO.getName(), savedTeacherDTO.getName());
    verify(teacherRepository, times(1)).save(any(Teacher.class));
  }

  @Test
  void save_exception() {
    when(teacherRepository.save(any(Teacher.class))).thenThrow(
        new RuntimeException("Database error"));

    TeacherResourceException exception = assertThrows(TeacherResourceException.class,
        () -> teacherService.save(teacherDTO));

    assertEquals("Error saving teacher: Database error", exception.getMessage());
  }

  @Test
  void update_success() {
    when(teacherRepository.findById(anyLong())).thenReturn(Optional.of(teacher));
    when(teacherRepository.save(any(Teacher.class))).thenReturn(teacher);

    TeacherDTO updatedTeacherDTO = teacherService.update(1L, teacherDTO);

    assertNotNull(updatedTeacherDTO);
    assertEquals(teacherDTO.getName(), updatedTeacherDTO.getName());
    verify(teacherRepository, times(1)).findById(anyLong());
    verify(teacherRepository, times(1)).save(any(Teacher.class));
  }

  @Test
  void update_notFound() {
    when(teacherRepository.findById(anyLong())).thenReturn(Optional.empty());

    TeacherResourceException exception = assertThrows(TeacherResourceException.class,
        () -> teacherService.update(1L, teacherDTO));

    assertEquals("Error updating teacher with id: 1. Teacher not found with id: 1",
        exception.getMessage());
  }

  @Test
  void update_exception() {
    when(teacherRepository.findById(anyLong())).thenReturn(Optional.of(teacher));
    when(teacherRepository.save(any(Teacher.class))).thenThrow(
        new RuntimeException("Database error"));

    TeacherResourceException exception = assertThrows(TeacherResourceException.class,
        () -> teacherService.update(1L, teacherDTO));

    assertEquals("Error updating teacher with id: 1. Database error",
        exception.getMessage());
  }

  @Test
  void delete_success() {
    when(teacherRepository.existsById(anyLong())).thenReturn(true);
    doNothing().when(teacherRepository).deleteById(anyLong());

    teacherService.delete(1L);

    verify(teacherRepository, times(1)).existsById(anyLong());
    verify(teacherRepository, times(1)).deleteById(anyLong());
  }

  @Test
  void delete_notFound() {
    when(teacherRepository.existsById(anyLong())).thenReturn(false);

    TeacherResourceException exception = assertThrows(TeacherResourceException.class,
        () -> teacherService.delete(1L));

    assertEquals("Error deleting teacher with id: 1. Teacher not found with id: 1",
        exception.getMessage());
  }

  @Test
  void delete_exception() {
    when(teacherRepository.existsById(anyLong())).thenReturn(true);
    doThrow(new RuntimeException("Database error")).when(teacherRepository)
        .deleteById(anyLong());

    TeacherResourceException exception = assertThrows(TeacherResourceException.class,
        () -> teacherService.delete(1L));

    assertEquals("Error deleting teacher with id: 1. Database error",
        exception.getMessage());
  }
}
