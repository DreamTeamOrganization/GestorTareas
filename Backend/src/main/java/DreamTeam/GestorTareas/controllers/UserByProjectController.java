package DreamTeam.GestorTareas.controllers;

import DreamTeam.GestorTareas.dtos.UserSimpleDto;
import DreamTeam.GestorTareas.entities.UserByProjectEntity;
import DreamTeam.GestorTareas.repositories.ProjectRepository;
import DreamTeam.GestorTareas.repositories.UserByProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/userByProject")
public class UserByProjectController {

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    UserByProjectRepository userByProjectRepository;

    @GetMapping("/{idProject}")
    public ResponseEntity<?> getUsersByProject(@PathVariable Long idProject){
        //Verificar que el proyecto exista
        if (projectRepository.findById(idProject).isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Proyecto no encontrado");
        }

        try{
            List<UserSimpleDto> usersByProject = userByProjectRepository.findUsersByProjectId(idProject);
            return ResponseEntity.ok(usersByProject);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addUserByProject(@RequestBody UserByProjectEntity newUserByProject){
        try{
            UserByProjectEntity savedUser = userByProjectRepository.save(newUserByProject);
            return ResponseEntity.ok(savedUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }
    }

    @DeleteMapping("/{idUserByProject}")
    public ResponseEntity<?> deleteUsersByProject(@PathVariable Long idUserByProject){
        //Validar que el usar by project exista
        if (userByProjectRepository.findById(idUserByProject).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User by project no encontrado");
        }
        try{
            userByProjectRepository.deleteById(idUserByProject);
            return ResponseEntity.ok("User by project borrado");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }
    }

}
