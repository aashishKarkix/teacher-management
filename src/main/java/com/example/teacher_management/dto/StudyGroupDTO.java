package com.example.teacher_management.dto;

import com.example.teacher_management.entity.StudyGroup;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudyGroupDTO {
  private Long groupId;
  private String groupName;
  private String description;

  public static StudyGroupDTO fromEntity(StudyGroup studyGroup) {
    return new StudyGroupDTO(
        studyGroup.getGroupId(),
        studyGroup.getGroupName(),
        studyGroup.getDescription()
    );
  }

  public StudyGroup toEntity() {
    StudyGroup studyGroup = new StudyGroup();
    studyGroup.setGroupId(this.groupId);
    studyGroup.setGroupName(this.groupName);
    studyGroup.setDescription(this.description);
    return studyGroup;
  }
}
