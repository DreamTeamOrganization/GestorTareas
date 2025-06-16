package DreamTeam.GestorTareas.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "task_types_by_project")
public class TaskTypeByProjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_project", nullable = false)
    private ProjectEntity project;

    @ManyToOne
    @JoinColumn(name = "id_task_type", nullable = false)
    private TaskTypeEntity taskType;

}
