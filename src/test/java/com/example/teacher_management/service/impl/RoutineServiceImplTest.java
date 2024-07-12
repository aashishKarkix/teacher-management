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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoutineServiceImplTest {

  @Mock
  private RoutineRepository routineRepository;

  @Mock
  private TeacherRepository teacherRepository;

  @Mock
  private GroupRepository groupRepository;

  @InjectMocks
  private RoutineServiceImpl routineService;

  private RoutineDTO routineDTO;
  private Routine routine;
  private Teacher teacher;
  private StudyGroup studyGroup;

  @BeforeEach
  void setUp() {
    teacher = new Teacher();
    teacher.setTeacherId(1L);
    teacher.setName("Aashish Karki");

    studyGroup = new StudyGroup();
    studyGroup.setGroupId(1L);

    routineDTO = new RoutineDTO();
    routineDTO.setRoutineId(1L);
    routineDTO.setTeacherId(1L);
    routineDTO.setGroupId(1L);
    routineDTO.setRoutineDate(LocalDate.now());
    routineDTO.setStartTime(LocalTime.of(9, 0));
    routineDTO.setEndTime(LocalTime.of(10, 0));

    routine = new Routine();
    routine.setRoutineId(1L);
    routine.setTeacher(teacher);
    routine.setStudyGroup(studyGroup);
    routine.setRoutineDate(LocalDate.now());
    routine.setStartTime(LocalTime.of(9, 0));
    routine.setEndTime(LocalTime.of(10, 0));
  }

  @Test
  void saveRoutine_success() {
    when(teacherRepository.findById(anyLong())).thenReturn(Optional.of(teacher));
    when(groupRepository.findById(anyLong())).thenReturn(Optional.of(studyGroup));
    when(routineRepository.save(any(Routine.class))).thenReturn(routine);

    RoutineDTO savedRoutine = routineService.saveRoutine(routineDTO);

    assertNotNull(savedRoutine);
    verify(routineRepository, times(1)).save(any(Routine.class));
  }

  @Test
  void saveRoutine_teacherNotFound() {
    when(teacherRepository.findById(anyLong())).thenReturn(Optional.empty());

    RoutineResourceException exception = assertThrows(RoutineResourceException.class,
        () -> routineService.saveRoutine(routineDTO));

    assertEquals(
        "Error while saving routine: Cannot invoke \"com.example.teacher_management.entity.Routine.getRoutineId()\" because \"routine\" is null",
        exception.getMessage());
  }

  @Test
  void updateRoutine_success() {
    when(routineRepository.findById(anyLong())).thenReturn(Optional.of(routine));
    when(teacherRepository.findById(anyLong())).thenReturn(Optional.of(teacher));
    when(groupRepository.findById(anyLong())).thenReturn(Optional.of(studyGroup));
    when(routineRepository.save(any(Routine.class))).thenReturn(routine);

    RoutineDTO updatedRoutine = routineService.updateRoutine(1L, routineDTO);

    assertNotNull(updatedRoutine);
    verify(routineRepository, times(1)).save(any(Routine.class));
  }

  @Test
  void updateRoutine_notFound() {
    when(routineRepository.findById(anyLong())).thenReturn(Optional.empty());

    RoutineResourceException exception = assertThrows(RoutineResourceException.class,
        () -> routineService.updateRoutine(1L, routineDTO));

    assertEquals("Error updating routine with id 1: Routine not found with id: 1",
        exception.getMessage());
  }

  @Test
  void deleteRoutine_success() {
    when(routineRepository.existsById(anyLong())).thenReturn(true);
    doNothing().when(routineRepository).deleteById(anyLong());

    routineService.deleteRoutine(1L);

    verify(routineRepository, times(1)).deleteById(anyLong());
  }

  @Test
  void deleteRoutine_notFound() {
    when(routineRepository.existsById(anyLong())).thenReturn(false);

    RoutineResourceException exception = assertThrows(RoutineResourceException.class,
        () -> routineService.deleteRoutine(1L));

    assertEquals("Error deleting routine with id 1: Routine not found with id: 1",
        exception.getMessage());
  }

  @Test
  void getAllRoutines_success() {
    when(routineRepository.findAll()).thenReturn(List.of(routine));

    List<RoutineDTO> routines = routineService.getAllRoutines();

    assertFalse(routines.isEmpty());
    verify(routineRepository, times(1)).findAll();
  }

  @Test
  void getRoutineById_success() {
    when(routineRepository.findById(anyLong())).thenReturn(Optional.of(routine));

    Optional<RoutineDTO> routineOptional = routineService.getRoutineById(1L);

    assertTrue(routineOptional.isPresent());
    verify(routineRepository, times(1)).findById(anyLong());
  }

  @Test
  void getRoutineById_notFound() {
    when(routineRepository.findById(anyLong())).thenReturn(Optional.empty());

    RoutineResourceException exception = assertThrows(RoutineResourceException.class,
        () -> routineService.getRoutineById(1L));

    assertEquals("Error fetching routine with id 1: Routine not found with id: 1",
        exception.getMessage());
  }

  @Test
  void calculateTeacherWorkload_success() {
    when(teacherRepository.findByName(anyString())).thenReturn(Optional.of(teacher));
    when(routineRepository.findByTeacherAndRoutineDateBetween(any(Teacher.class),
        any(LocalDate.class), any(LocalDate.class))).thenReturn(List.of(routine));

    long workload = routineService.calculateTeacherWorkload("Aashish Karki",
        LocalDate.now(), LocalDate.now());

    assertEquals(1, workload);
    verify(routineRepository, times(1)).findByTeacherAndRoutineDateBetween(
        any(Teacher.class), any(LocalDate.class), any(LocalDate.class));
  }

  @Test
  void calculateTeacherWorkload_teacherNotFound() {
    when(teacherRepository.findByName(anyString())).thenReturn(Optional.empty());

    TeacherResourceException exception = assertThrows(TeacherResourceException.class,
        () -> routineService.calculateTeacherWorkload("Aashish Karki", LocalDate.now(),
            LocalDate.now()));

    assertEquals("Teacher not found with name: Aashish Karki", exception.getMessage());
  }

  @Test
  void calculateGroupWorkload_success() {
    when(groupRepository.findById(anyLong())).thenReturn(Optional.of(studyGroup));
    when(routineRepository.findByStudyGroup(any(StudyGroup.class))).thenReturn(
        List.of(routine));

    long workload = routineService.calculateGroupWorkload(1L);

    assertEquals(1, workload);
    verify(routineRepository, times(1)).findByStudyGroup(any(StudyGroup.class));
  }

  @Test
  void calculateGroupWorkload_groupNotFound() {
    when(groupRepository.findById(anyLong())).thenReturn(Optional.empty());

    GroupResourceException exception = assertThrows(GroupResourceException.class,
        () -> routineService.calculateGroupWorkload(1L));

    assertEquals("Group not found with id: 1", exception.getMessage());
  }
}

