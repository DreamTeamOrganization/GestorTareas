package DreamTeam.GestorTareas.repositories;

import DreamTeam.GestorTareas.entities.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<UsersEntity, Long> {
    UsersEntity findByUsernameAndPassword(String username, String password);
}
