package DreamTeam.GestorTareas.repositories;

import DreamTeam.GestorTareas.dtos.UserSimpleDto;
import DreamTeam.GestorTareas.entities.TaskCollabEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskCollabRepository extends JpaRepository<TaskCollabEntity, Long> {
    List<TaskCollabEntity> findByTaskId (Long idTask);

    @Query("SELECT new DreamTeam.GestorTareas.dtos.UserSimpleDto(u.id, u.username) " +
            "FROM TaskCollabEntity tce " +
            "JOIN tce.user u " +
            "WHERE tce.task.id = :idTask")
    List<UserSimpleDto> findUsersByTaskId (@Param("idTask") Long idTask);
}