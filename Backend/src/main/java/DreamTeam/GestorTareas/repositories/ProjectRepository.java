package DreamTeam.GestorTareas.repositories;

import DreamTeam.GestorTareas.entities.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {
}
