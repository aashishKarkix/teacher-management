package com.example.teacher_management.service.impl;

import com.example.teacher_management.dto.StudyGroupDTO;
import com.example.teacher_management.entity.StudyGroup;
import com.example.teacher_management.exception.GroupResourceException;
import com.example.teacher_management.repository.GroupRepository;
import com.example.teacher_management.service.StudyGroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StudyGroupServiceImpl implements StudyGroupService {

  private final GroupRepository studyGroupRepository;

  public StudyGroupServiceImpl(GroupRepository studyGroupRepository) {
    this.studyGroupRepository = studyGroupRepository;
  }

  @Override
  public StudyGroup findById(Long id) {
    log.info("Fetching study group with id: {}", id);
    return studyGroupRepository.findById(id).orElseThrow(() -> {
      log.error("Group resource not found with id: {}", id);
      return new GroupResourceException("Group resource not found with id: " + id);
    });
  }

  @Override
  public StudyGroupDTO save(StudyGroupDTO groupDto) {
    log.info("Saving study group: {}", groupDto);
    try {
      StudyGroup studyGroup = groupDto.toEntity();
      StudyGroup savedGroup = studyGroupRepository.save(studyGroup);

      log.info("Saved study group: {}", savedGroup);
      return StudyGroupDTO.fromEntity(savedGroup);
    } catch (Exception e) {
      log.error("Error saving study group: {}", e.getMessage());
      throw new GroupResourceException("Error saving study group: " + e.getMessage());
    }
  }

  @Override
  public StudyGroupDTO update(Long id, StudyGroupDTO groupDto) {
    log.info("Updating study group with id: {}", id);
    try {
      StudyGroup existingGroup = studyGroupRepository.findById(id).orElseThrow(() -> {
        log.error("Group not found with id: {}", id);
        return new GroupResourceException("Group not found with id: " + id);
      });

      existingGroup.setGroupName(groupDto.getGroupName());
      existingGroup.setDescription(groupDto.getDescription());

      StudyGroup updatedGroup = studyGroupRepository.save(existingGroup);
      log.info("Updated study group: {}", updatedGroup);

      return StudyGroupDTO.fromEntity(updatedGroup);
    } catch (Exception e) {
      log.error("Error updating study group with id: {}: {}", id, e.getMessage());
      throw new GroupResourceException(
          "Error updating study group with id: " + id + ". " + e.getMessage());
    }
  }

  @Override
  public void delete(Long id) {
    log.info("Deleting study group with id: {}", id);
    try {
      if (!studyGroupRepository.existsById(id)) {
        throw new GroupResourceException("Group not found with id: " + id);
      }
      studyGroupRepository.deleteById(id);
      log.info("Deleted study group with id: {}", id);
    } catch (Exception e) {
      log.error("Error deleting study group with id: {}: {}", id, e.getMessage());
      throw new GroupResourceException(
          "Error deleting study group with id: " + id + ". " + e.getMessage());
    }
  }
}
