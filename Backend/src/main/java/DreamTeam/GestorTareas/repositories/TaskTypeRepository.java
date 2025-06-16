package DreamTeam.GestorTareas.repositories;

import DreamTeam.GestorTareas.entities.TaskTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskTypeRepository extends JpaRepository<TaskTypeEntity, Long> {

}
