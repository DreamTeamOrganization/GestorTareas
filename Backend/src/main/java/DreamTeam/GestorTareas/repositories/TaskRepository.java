package DreamTeam.GestorTareas.repositories;

import DreamTeam.GestorTareas.entities.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    List<TaskEntity> findByProjectId(Long idProject);
}
