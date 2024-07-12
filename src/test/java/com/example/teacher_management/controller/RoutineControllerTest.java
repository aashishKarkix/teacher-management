package com.example.teacher_management.controller;

import com.example.teacher_management.dto.RoutineDTO;
import com.example.teacher_management.exception.IdNotFoundException;
import com.example.teacher_management.exception.RoutineResourceException;
import com.example.teacher_management.exception.TeacherResourceException;
import com.example.teacher_management.service.RoutineService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoutineControllerTest {

  @Mock
  private RoutineService routineService;

  @InjectMocks
  private RoutineController routineController;

  private RoutineDTO routineDTO;

  @BeforeEach
  void setUp() {
    routineDTO = new RoutineDTO();
    routineDTO.setRoutineId(1L);
  }

  @Test
  void createRoutine_success() {
    when(routineService.saveRoutine(any(RoutineDTO.class))).thenReturn(routineDTO);

    ResponseEntity<RoutineDTO> response = routineController.createRoutine(routineDTO);

    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertEquals(routineDTO, response.getBody());
    verify(routineService, times(1)).saveRoutine(any(RoutineDTO.class));
  }

  @Test
  void createRoutine_nullRoutine() {
    RoutineResourceException exception = assertThrows(RoutineResourceException.class,
        () -> routineController.createRoutine(null));

    assertEquals("Error creating routine: Routine resource was not found",
        exception.getMessage());
  }

  @Test
  void getAllRoutines_success() {
    List<RoutineDTO> routines = Collections.singletonList(routineDTO);
    when(routineService.getAllRoutines()).thenReturn(routines);

    List<RoutineDTO> response = routineController.getAllRoutines();

    assertEquals(1, response.size());
    assertEquals(routineDTO, response.get(0));
    verify(routineService, times(1)).getAllRoutines();
  }

  @Test
  void getRoutineById_success() {
    when(routineService.getRoutineById(anyLong())).thenReturn(Optional.of(routineDTO));

    ResponseEntity<RoutineDTO> response = routineController.getRoutineById(1L);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(routineDTO, response.getBody());
    verify(routineService, times(1)).getRoutineById(anyLong());
  }

  @Test
  void getRoutineById_notFound() {
    when(routineService.getRoutineById(anyLong())).thenReturn(Optional.empty());

    IdNotFoundException exception = assertThrows(IdNotFoundException.class,
        () -> routineController.getRoutineById(1L));

    assertEquals("Routine not found with id: 1", exception.getMessage());
  }

  @Test
  void updateRoutine_success() {
    when(routineService.updateRoutine(anyLong(), any(RoutineDTO.class))).thenReturn(
        routineDTO);

    ResponseEntity<RoutineDTO> response = routineController.updateRoutine(1L, routineDTO);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(routineDTO, response.getBody());
    verify(routineService, times(1)).updateRoutine(anyLong(), any(RoutineDTO.class));
  }

  @Test
  void updateRoutine_invalidId() {
    IdNotFoundException exception = assertThrows(IdNotFoundException.class,
        () -> routineController.updateRoutine(0L, routineDTO));

    assertEquals("Id is not valid or Defined", exception.getMessage());
  }

  @Test
  void deleteRoutine_success() {
    doNothing().when(routineService).deleteRoutine(anyLong());

    ResponseEntity<Void> response = routineController.deleteRoutine(1L);

    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    verify(routineService, times(1)).deleteRoutine(anyLong());
  }

  @Test
  void deleteRoutine_invalidId() {
    IdNotFoundException exception = assertThrows(IdNotFoundException.class,
        () -> routineController.deleteRoutine(0L));

    assertEquals("Id is not valid or Defined", exception.getMessage());
  }

  @Test
  void getTeacherWorkload_success() {
    when(routineService.calculateTeacherWorkload(anyString(), any(LocalDate.class),
        any(LocalDate.class))).thenReturn(10L);

    long workload = routineController.getTeacherWorkload("Aashish Karki", "2023-01-01",
        "2023-01-31");

    assertEquals(10L, workload);
    verify(routineService, times(1)).calculateTeacherWorkload(anyString(),
        any(LocalDate.class), any(LocalDate.class));
  }

  @Test
  void getTeacherWorkload_invalidParameters() {
    TeacherResourceException exception = assertThrows(TeacherResourceException.class,
        () -> routineController.getTeacherWorkload("", "2023-01-01", "2023-01-31"));

    assertEquals(
        "Error calculating teacher workload: Provided teacher resources are null or empty",
        exception.getMessage());
  }

  @Test
  void getGroupWorkload_success() {
    when(routineService.calculateGroupWorkload(anyLong())).thenReturn(20L);

    long workload = routineController.getGroupWorkload(1L);

    assertEquals(20L, workload);
    verify(routineService, times(1)).calculateGroupWorkload(anyLong());
  }

  @Test
  void getGroupWorkload_invalidId() {
    RoutineResourceException exception = assertThrows(RoutineResourceException.class,
        () -> routineController.getGroupWorkload(0L));

    assertEquals("Error calculating group workload: Id is missing or Empty: 0",
        exception.getMessage());
  }
}
