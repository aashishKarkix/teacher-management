package com.example.teacher_management.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import com.example.teacher_management.dto.StudyGroupDTO;
import com.example.teacher_management.entity.StudyGroup;
import com.example.teacher_management.exception.GroupResourceException;
import com.example.teacher_management.repository.GroupRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudyGroupServiceImplTest {

  @Mock
  private GroupRepository studyGroupRepository;

  @InjectMocks
  private StudyGroupServiceImpl studyGroupService;

  private StudyGroup studyGroup;
  private StudyGroupDTO studyGroupDTO;

  @BeforeEach
  void setUp() {
    studyGroup = new StudyGroup();
    studyGroup.setGroupId(1L);
    studyGroup.setGroupName("IT Group");
    studyGroup.setDescription("Group for IT enthusiasts");

    studyGroupDTO = new StudyGroupDTO();
    studyGroupDTO.setGroupId(1L);
    studyGroupDTO.setGroupName("IT Group");
    studyGroupDTO.setDescription("Group for IT enthusiasts");
  }

  @Test
  void findById_success() {
    when(studyGroupRepository.findById(anyLong())).thenReturn(Optional.of(studyGroup));

    StudyGroup foundGroup = studyGroupService.findById(1L);

    assertNotNull(foundGroup);
    assertEquals(studyGroup.getGroupId(), foundGroup.getGroupId());
    verify(studyGroupRepository, times(1)).findById(anyLong());
  }

  @Test
  void findById_notFound() {
    when(studyGroupRepository.findById(anyLong())).thenReturn(Optional.empty());

    GroupResourceException exception = assertThrows(GroupResourceException.class,
        () -> studyGroupService.findById(1L));

    assertEquals("Group resource not found with id: 1", exception.getMessage());
  }

  @Test
  void save_success() {
    when(studyGroupRepository.save(any(StudyGroup.class))).thenReturn(studyGroup);

    StudyGroupDTO savedGroupDTO = studyGroupService.save(studyGroupDTO);

    assertNotNull(savedGroupDTO);
    assertEquals(studyGroupDTO.getGroupName(), savedGroupDTO.getGroupName());
    verify(studyGroupRepository, times(1)).save(any(StudyGroup.class));
  }

  @Test
  void save_exception() {
    when(studyGroupRepository.save(any(StudyGroup.class))).thenThrow(
        new RuntimeException("Database error"));

    GroupResourceException exception = assertThrows(GroupResourceException.class,
        () -> studyGroupService.save(studyGroupDTO));

    assertEquals("Error saving study group: Database error", exception.getMessage());
  }

  @Test
  void update_success() {
    when(studyGroupRepository.findById(anyLong())).thenReturn(Optional.of(studyGroup));
    when(studyGroupRepository.save(any(StudyGroup.class))).thenReturn(studyGroup);

    StudyGroupDTO updatedGroupDTO = studyGroupService.update(1L, studyGroupDTO);

    assertNotNull(updatedGroupDTO);
    assertEquals(studyGroupDTO.getGroupName(), updatedGroupDTO.getGroupName());
    verify(studyGroupRepository, times(1)).findById(anyLong());
    verify(studyGroupRepository, times(1)).save(any(StudyGroup.class));
  }

  @Test
  void update_notFound() {
    when(studyGroupRepository.findById(anyLong())).thenReturn(Optional.empty());

    GroupResourceException exception = assertThrows(GroupResourceException.class,
        () -> studyGroupService.update(1L, studyGroupDTO));

    assertEquals("Error updating study group with id: 1. Group not found with id: 1",
        exception.getMessage());
  }

  @Test
  void update_exception() {
    when(studyGroupRepository.findById(anyLong())).thenReturn(Optional.of(studyGroup));
    when(studyGroupRepository.save(any(StudyGroup.class))).thenThrow(
        new RuntimeException("Database error"));

    GroupResourceException exception = assertThrows(GroupResourceException.class,
        () -> studyGroupService.update(1L, studyGroupDTO));

    assertEquals("Error updating study group with id: 1. Database error",
        exception.getMessage());
  }

  @Test
  void delete_success() {
    when(studyGroupRepository.existsById(anyLong())).thenReturn(true);
    doNothing().when(studyGroupRepository).deleteById(anyLong());

    studyGroupService.delete(1L);

    verify(studyGroupRepository, times(1)).existsById(anyLong());
    verify(studyGroupRepository, times(1)).deleteById(anyLong());
  }

  @Test
  void delete_notFound() {
    when(studyGroupRepository.existsById(anyLong())).thenReturn(false);

    GroupResourceException exception = assertThrows(GroupResourceException.class,
        () -> studyGroupService.delete(1L));

    assertEquals("Error deleting study group with id: 1. Group not found with id: 1",
        exception.getMessage());
  }

  @Test
  void delete_exception() {
    when(studyGroupRepository.existsById(anyLong())).thenReturn(true);
    doThrow(new RuntimeException("Database error")).when(studyGroupRepository)
        .deleteById(anyLong());

    GroupResourceException exception = assertThrows(GroupResourceException.class,
        () -> studyGroupService.delete(1L));

    assertEquals("Error deleting study group with id: 1. Database error",
        exception.getMessage());
  }
}
