package DreamTeam.GestorTareas.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "tasks")
public class TaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "id_project", nullable = false)
    private ProjectEntity project;

    @ManyToOne
    @JoinColumn(name = "id_task")
    private TaskEntity task;

    @ManyToOne
    @JoinColumn(name = "id_task_status", nullable = false)
    private TaskStatusEntity taskStatus;

    @ManyToOne
    @JoinColumn(name = "id_task_type", nullable = false)
    private TaskTypeEntity taskType;

    @Column(nullable = false)
    private String title;

    private String description;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private UserEntity user;

    private int priority;

    private LocalDate startDate;

    private LocalDate endDate;

}
