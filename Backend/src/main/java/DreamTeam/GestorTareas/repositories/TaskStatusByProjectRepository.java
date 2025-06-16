package DreamTeam.GestorTareas.repositories;

import DreamTeam.GestorTareas.entities.TaskStatusByProjectEntity;
import DreamTeam.GestorTareas.entities.TaskStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface TaskStatusByProjectRepository extends JpaRepository<TaskStatusByProjectEntity, Long> {
    @Query("SELECT new DreamTeam.GestorTareas.dtos.TaskStatusSimpleDto(s.id, s.name) " +
            "FROM TaskStatusByProjectEntity tsp " +
            "JOIN tsp.status s " +
            "WHERE tsp.project.id = :idProject")
    List<TaskStatusEntity> findTaskStatusByIdProject(@Param("idProject") Long idProject);
}
