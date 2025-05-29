package DreamTeam.GestorTareas.repositories;


import DreamTeam.GestorTareas.entities.TaskStatusEntity;
import DreamTeam.GestorTareas.entities.TaskTypeByProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskTypeByProjectRepository extends JpaRepository<TaskTypeByProjectEntity, Long> {
    @Query("SELECT NEW DreamTeam.GestorTareas.dtos.TaskTypeSimpleDto (tt.id, tt.name)" +
            "FROM TaskTypeByProjectEntity ttp" +
            "JOIN ttp.taskType tt" +
            "WHERE ttp.project.id = :idProject")
    List<TaskStatusEntity> findTaskTypeByIdProject(@Param("idProject") Long idProject);
}
