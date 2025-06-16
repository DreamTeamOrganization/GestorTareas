package DreamTeam.GestorTareas.controllers;

import DreamTeam.GestorTareas.dtos.ProjectAndUserDto;
import DreamTeam.GestorTareas.dtos.ProjectNameDto;
import DreamTeam.GestorTareas.dtos.ProjectWithUsersDto;
import DreamTeam.GestorTareas.dtos.UserSimpleDto;
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

    //Obtener proyectos de un usuario
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
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }
    }

    //Agregar proyecto mas relacion con usuario
    @PostMapping("/add")
    public ResponseEntity<?> saveNewProjec(@RequestBody ProjectAndUserDto projectAndUserDto){
        //validar que exista un usuario
        if (usersRepository.findById(projectAndUserDto.getIdUser()).isPresent()){
            try{
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
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
            }

        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }
    }

    //Obtener proyecto por id
    @GetMapping("/{id}")
    public ResponseEntity<?> getProject(@PathVariable Long id){
        //Buscar el proyecto
        Optional<ProjectEntity> projectOpt = projectRepository.findById(id);
        if (projectOpt.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Proyecto no encontrado");
        } else {
            return ResponseEntity.ok(projectOpt.get());
        }
    }

    //Actualizar proyecto por id
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProject(@PathVariable Long id, @RequestBody ProjectEntity updatedProject){
        //Buscar el proyecto
        Optional<ProjectEntity> projectOpt = projectRepository.findById(id);
        if (projectOpt.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Proyecto no encontrado");
        }

        try{
            ProjectEntity project = projectOpt.get();
            project.setName(updatedProject.getName() != null
                    ? updatedProject.getName()
                    : project.getName()
            );
            project.setDescription(updatedProject.getDescription() != null
                    ? updatedProject.getDescription()
                    : project.getDescription()
            );

            projectRepository.save(project);
            return ResponseEntity.ok(project);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }
    }

    //Borrar un proyecto mas relacion con usuarios
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable Long id){
        //Buscar el proyecto
        Optional<ProjectEntity> projectOpt = projectRepository.findById(id);
        if (projectOpt.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Proyecto no encontrado");
        }

        try{
            //Borrar relacion con usuarios
            List<UserByProjectEntity> relations = userByProjectRepository.findByProjectId(id);
            userByProjectRepository.deleteAll(relations);

            //Borrar proyecto
            projectRepository.deleteById(id);

            return ResponseEntity.ok("Borrado exitoso");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }
    }

    //Obtener proyecto mas info de usuarios
    @GetMapping("/{id}/with-users")
    public ResponseEntity<?> getProjectWithUsers(@PathVariable Long id){
        Optional<ProjectEntity> projectOpt = projectRepository.findById(id);
        if (projectOpt.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Proyecto no encontrado");
        }

        try{
            ProjectEntity project = projectOpt.get();

            List<UserSimpleDto> users = userByProjectRepository.findUsersByProjectId(id);

            ProjectWithUsersDto projectWithUsers = new ProjectWithUsersDto(
                    project.getId(),
                    project.getName(),
                    project.getDescription(),
                    users
            );

            return ResponseEntity.ok(projectWithUsers);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }
    }

}
