package DreamTeam.GestorTareas.dtos;

import DreamTeam.GestorTareas.entities.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TaskSimpleDto {

    private long id;
    private ProjectEntity project;
    private TaskEntity parentTask;
    private TaskStatusEntity taskStatus;
    private TaskTypeEntity taskType;
    private String title;
    private String description;
    private UserEntity user;
    private Integer priority;
    private LocalDate startDate;
    private LocalDate endDate;

    public TaskSimpleDto(long id, ProjectEntity project, TaskEntity parentTask, TaskStatusEntity taskStatus, TaskTypeEntity taskType, String title, String description, UserEntity user, Integer priority, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.project = project;
        this.parentTask = parentTask;
        this.taskStatus = taskStatus;
        this.taskType = taskType;
        this.title = title;
        this.description = description;
        this.user = user;
        this.priority = priority;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}