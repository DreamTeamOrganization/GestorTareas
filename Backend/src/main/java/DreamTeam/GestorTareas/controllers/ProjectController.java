package DreamTeam.GestorTareas.controllers;

import DreamTeam.GestorTareas.dtos.ProjectNameDto;
import DreamTeam.GestorTareas.entities.ProjectEntity;
import DreamTeam.GestorTareas.entities.UserByProjectEntity;
import DreamTeam.GestorTareas.repositories.UserByProjectRepository;
import DreamTeam.GestorTareas.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("api/projects")
public class ProjectController {

    @Autowired
    private UserByProjectRepository userByProjectRepository;

    @Autowired
    private UsersRepository usersRepository;

    @GetMapping("/user/{idUser}")
    public ResponseEntity<?> getProjectsByUser(@PathVariable Long idUser){

        //validar que exista un usuario
        if (usersRepository.findById(idUser).isPresent()){
            //listado de proyectos donde aparece un usuario
            List<UserByProjectEntity> userProjects = userByProjectRepository.findByUserId(idUser);

            //extraer solo los proyectos usando el projectName dto
            List<ProjectNameDto> projects = userProjects.stream()
                    .map(up -> new ProjectNameDto(up.getProject().getId(), up.getProject().getName()))
                    .toList();

            return ResponseEntity.ok(projects);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }
    }
}
