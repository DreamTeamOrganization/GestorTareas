package DreamTeam.GestorTareas.repositories;

import DreamTeam.GestorTareas.entities.UserByProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserByProjectRepository extends JpaRepository<UserByProjectEntity, Long> {
    List<UserByProjectEntity> findByUserId(Long idUser);
}
