package DreamTeam.GestorTareas.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "task_statuses_by_project")
public class TaskStatuByProjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_project", nullable = false)
    private ProjectEntity project;

    @ManyToOne
    @JoinColumn(name = "id_status", nullable = false)
    private TaskStatusEntity status;

    @Column(nullable = false)
    private int priority;
}
