package com.example.teacher_management.dto;

import com.example.teacher_management.entity.Teacher;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TeacherDTO {
  private Long teacherId;
  private String name;
  private String department;
  private String email;

  public static TeacherDTO fromEntity(Teacher teacher) {
    return new TeacherDTO(
        teacher.getTeacherId(),
        teacher.getName(),
        teacher.getDepartment(),
        teacher.getEmail()
    );
  }

  public Teacher toEntity() {
    Teacher teacher = new Teacher();
    teacher.setTeacherId(this.teacherId);
    teacher.setName(this.name);
    teacher.setDepartment(this.department);
    teacher.setEmail(this.email);
    return teacher;
  }
}
