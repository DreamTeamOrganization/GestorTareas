package DreamTeam.GestorTareas.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "task_collabs")
public class TaskCollabEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_task", nullable = false)
    private TaskEntity task;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private UserEntity user;
}
