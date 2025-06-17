package DreamTeam.GestorTareas.controllers;

import DreamTeam.GestorTareas.dtos.TaskStatusSimpleDto;
import DreamTeam.GestorTareas.entities.TaskStatusByProjectEntity;
import DreamTeam.GestorTareas.entities.TaskStatusEntity;
import DreamTeam.GestorTareas.repositories.ProjectRepository;
import DreamTeam.GestorTareas.repositories.TaskStatusByProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/taskStatusProject")
public class TaskStatusByProjectController {

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    TaskStatusByProjectRepository taskStatusByProjectRepository;

    @GetMapping("/{idProject}")
    public ResponseEntity<?> getTaskStatus (@PathVariable Long idProject){
        //Validar que el proyecto exista
        if (projectRepository.findById(idProject).isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Proyecto no encontrado");
        };

        try{
            List<TaskStatusSimpleDto> taskStatus = taskStatusByProjectRepository.findTaskStatusByIdProject(idProject);
            return ResponseEntity.ok(taskStatus);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addTaskStatus (@RequestBody TaskStatusByProjectEntity newTaskStatus){
        try{
            TaskStatusByProjectEntity savedTask = taskStatusByProjectRepository.save(newTaskStatus);
            return ResponseEntity.ok(savedTask);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }
    }

    @DeleteMapping("/{idTaskStatus}")
    public ResponseEntity<?> deleteTaskStatus (@PathVariable Long idTaskStatus){
        //Verificar que el taskStatus exista
        if (taskStatusByProjectRepository.findById(idTaskStatus).isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task status no encontrado");
        }

        taskStatusByProjectRepository.deleteById(idTaskStatus);
        return ResponseEntity.ok("Task status borrado");
    }

}