package com.example.teacher_management.service.impl;

import com.example.teacher_management.dto.TeacherDTO;
import com.example.teacher_management.entity.Teacher;
import com.example.teacher_management.exception.TeacherResourceException;
import com.example.teacher_management.repository.TeacherRepository;
import com.example.teacher_management.service.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class TeacherServiceImpl implements TeacherService {

  private final TeacherRepository teacherRepository;

  public TeacherServiceImpl(TeacherRepository teacherRepository) {
    this.teacherRepository = teacherRepository;
  }

  @Override
  public TeacherDTO findById(Long id) {
    log.info("Fetching teacher with id: {}", id);
    Optional<Teacher> optionalTeacher = teacherRepository.findById(id);
    Teacher teacher = optionalTeacher.orElseThrow(() -> {
      log.error("Teacher not found with id: {}", id);
      return new TeacherResourceException("Teacher not found with id: " + id);
    });
    return TeacherDTO.fromEntity(teacher);
  }

  @Override
  public TeacherDTO save(TeacherDTO teacherDto) {
    log.info("Saving teacher: {}", teacherDto);
    try {
      Teacher teacher = teacherDto.toEntity();
      Teacher savedTeacher = teacherRepository.save(teacher);
      log.info("Saved teacher: {}", savedTeacher);
      return TeacherDTO.fromEntity(savedTeacher);
    } catch (Exception e) {
      log.error("Error saving teacher: {}", e.getMessage());
      throw new TeacherResourceException("Error saving teacher: " + e.getMessage());
    }
  }

  @Override
  public TeacherDTO update(Long id, TeacherDTO teacherDto) {
    log.info("Updating teacher with id: {}", id);
    try {
      Teacher existingTeacher = teacherRepository.findById(id).orElseThrow(() -> {
        log.error("Teacher not found for id: {}", id);
        return new TeacherResourceException("Teacher not found with id: " + id);
      });

      existingTeacher.setName(teacherDto.getName());
      existingTeacher.setDepartment(teacherDto.getDepartment());
      existingTeacher.setEmail(teacherDto.getEmail());

      Teacher updatedTeacher = teacherRepository.save(existingTeacher);
      log.info("Updated teacher: {}", updatedTeacher);
      return TeacherDTO.fromEntity(updatedTeacher);
    } catch (Exception e) {
      log.error("Error updating teacher with id: {}: {}", id, e.getMessage());
      throw new TeacherResourceException(
          "Error updating teacher with id: " + id + ". " + e.getMessage());
    }
  }

  @Override
  public void delete(Long id) {
    log.info("Deleting teacher with id: {}", id);
    try {
      if (!teacherRepository.existsById(id)) {
        throw new TeacherResourceException("Teacher not found with id: " + id);
      }
      teacherRepository.deleteById(id);
      log.info("Deleted teacher with id: {}", id);
    } catch (Exception e) {
      log.error("Error deleting teacher with id: {}: {}", id, e.getMessage());
      throw new TeacherResourceException(
          "Error deleting teacher with id: " + id + ". " + e.getMessage());
    }
  }
}
