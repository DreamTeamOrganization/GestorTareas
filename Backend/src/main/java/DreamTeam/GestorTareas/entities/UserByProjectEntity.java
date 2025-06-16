package DreamTeam.GestorTareas.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users_by_project")
public class UserByProjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="id_user", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "id_project", nullable = false)
    private ProjectEntity project;
}
