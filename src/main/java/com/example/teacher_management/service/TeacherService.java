package com.example.teacher_management.service;

import com.example.teacher_management.dto.TeacherDTO;
import com.example.teacher_management.entity.Teacher;

public interface TeacherService {
  TeacherDTO findById(Long id);
  TeacherDTO save(TeacherDTO teacher);
  TeacherDTO update(Long id, TeacherDTO teacher);
  void delete(Long id);
}
