package DreamTeam.GestorTareas.repositories;

import DreamTeam.GestorTareas.entities.TaskCollabEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskCollabRepository extends JpaRepository<TaskCollabEntity, Long> {
    List<TaskCollabEntity> findByTaskId (Long idTask);
}