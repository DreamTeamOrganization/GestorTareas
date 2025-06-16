package DreamTeam.GestorTareas.controllers;

import DreamTeam.GestorTareas.entities.TaskStatusByProjectEntity;
import DreamTeam.GestorTareas.entities.TaskStatusEntity;
import DreamTeam.GestorTareas.entities.TaskTypeByProjectEntity;
import DreamTeam.GestorTareas.repositories.ProjectRepository;
import DreamTeam.GestorTareas.repositories.TaskTypeByProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/taskTypeProject")
public class TaskTypeByProjectController {

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    TaskTypeByProjectRepository taskTypeByProjectRepository;

    @GetMapping("/{idProject}")
    public ResponseEntity<?> getTaskType (@PathVariable Long idProject){
        //Validar que el proyecto exista
        if (projectRepository.findById(idProject).isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Proyecto no encontrado");
        };

        try{
            List<TaskStatusEntity> taskType = taskTypeByProjectRepository.findTaskTypeByIdProject(idProject);
            return ResponseEntity.ok(taskType);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addTaskStatus (@RequestBody TaskTypeByProjectEntity newTaskType){
        try{
            TaskTypeByProjectEntity savedTask = taskTypeByProjectRepository.save(newTaskType);
            return ResponseEntity.ok(savedTask);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }
    }

    @DeleteMapping("/{idTaskType}")
    public ResponseEntity<?> deleteTaskStatus (@PathVariable Long idTaskType){
        //Verificar que el taskStatus exista
        if (taskTypeByProjectRepository.findById(idTaskType).isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task type no encontrado");
        }

        taskTypeByProjectRepository.deleteById(idTaskType);
        return ResponseEntity.ok("Task type borrado");
    }
}
