package com.example.teacher_management.service;

import com.example.teacher_management.dto.StudyGroupDTO;
import com.example.teacher_management.entity.StudyGroup;

public interface StudyGroupService {
  StudyGroup findById(Long id);

  StudyGroupDTO save(StudyGroupDTO group);

  StudyGroupDTO update(Long id, StudyGroupDTO group);

  void delete(Long id);
}
