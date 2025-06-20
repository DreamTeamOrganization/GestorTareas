package DreamTeam.GestorTareas.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TaskSimpleDto {

    private Long project;
    private Long parentTask;
    private Long taskStatus;
    private Long taskType;
    private String title;
    private String description;
    private Long user;
    private Integer priority;
    private LocalDate startDate;
    private LocalDate endDate;

    public TaskSimpleDto(Long project, Long parentTask, Long taskStatus, Long taskType, String title, String description, Long user, Integer priority, LocalDate startDate, LocalDate endDate) {
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