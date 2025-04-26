package DreamTeam.GestorTareas.controllers;

import DreamTeam.GestorTareas.dtos.ProjectAndUserDto;
import DreamTeam.GestorTareas.dtos.ProjectNameDto;
import DreamTeam.GestorTareas.entities.ProjectEntity;
import DreamTeam.GestorTareas.entities.UserByProjectEntity;
import DreamTeam.GestorTareas.entities.UserEntity;
import DreamTeam.GestorTareas.repositories.ProjectRepository;
import DreamTeam.GestorTareas.repositories.UserByProjectRepository;
import DreamTeam.GestorTareas.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("api/projects")
public class ProjectController {

    @Autowired
    private UserByProjectRepository userByProjectRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private ProjectRepository projectRepository;

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

    @PostMapping("/add")
    public ResponseEntity<?> saveNewProjec(@RequestBody ProjectAndUserDto projectAndUserDto){
        //validar que exista un usuario
        if (usersRepository.findById(projectAndUserDto.getIdUser()).isPresent()){
            //Crear el nuevo proyecto
            ProjectEntity newProject = new ProjectEntity();
            newProject.setName(projectAndUserDto.getName());
            newProject.setDescription(projectAndUserDto.getDescription());
            ProjectEntity savedProject = projectRepository.save(newProject);

            //Asociar usuario al proyecto
            Optional<UserEntity> user = usersRepository.findById(projectAndUserDto.getIdUser());

            UserByProjectEntity newUserByProject = new UserByProjectEntity();
            newUserByProject.setProject(savedProject);
            newUserByProject.setUser(user.get());
            userByProjectRepository.save(newUserByProject);

            return ResponseEntity.ok(savedProject);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }
    }
}
