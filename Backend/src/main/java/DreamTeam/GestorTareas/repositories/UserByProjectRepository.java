package DreamTeam.GestorTareas.repositories;

import DreamTeam.GestorTareas.dtos.UserSimpleDto;
import DreamTeam.GestorTareas.entities.UserByProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserByProjectRepository extends JpaRepository<UserByProjectEntity, Long> {
    List<UserByProjectEntity> findByUserId(Long idUser);
    List<UserByProjectEntity> findByProjectId(Long idProject);

    @Query("SELECT new DreamTeam.GestorTareas.dtos.UserSimpleDto(u.id, u.username) "+
            "FROM UserByProjectEntity up "+
            "JOIN up.user u " +
            "WHERE up.project.id = :projectId")
    List<UserSimpleDto> findUsersByProjectId(@Param("projectId") Long projectId);
}
