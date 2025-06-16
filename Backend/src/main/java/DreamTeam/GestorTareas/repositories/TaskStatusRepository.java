package DreamTeam.GestorTareas.repositories;

import DreamTeam.GestorTareas.entities.TaskStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskStatusRepository extends JpaRepository<TaskStatusEntity, Long> {

}
