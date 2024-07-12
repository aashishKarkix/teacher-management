package com.example.teacher_management.repository;

import com.example.teacher_management.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
  Optional<Teacher> findByName(String name);
}
