package com.example.teacher_management.repository;

import com.example.teacher_management.entity.StudyGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<StudyGroup, Long> {
}
