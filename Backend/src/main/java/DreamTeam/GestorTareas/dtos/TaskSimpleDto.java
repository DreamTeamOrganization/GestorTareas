package DreamTeam.GestorTareas.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TaskSimpleDto {

    private Long id;
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

    public TaskSimpleDto(long id, long project, long parentTask, long taskStatus, long taskType, String title, String description, long user, Integer priority, LocalDate startDate, LocalDate endDate) {
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