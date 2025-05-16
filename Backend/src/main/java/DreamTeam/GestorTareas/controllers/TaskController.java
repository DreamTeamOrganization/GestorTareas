package DreamTeam.GestorTareas.controllers;

import DreamTeam.GestorTareas.entities.*;
import DreamTeam.GestorTareas.repositories.ProjectRepository;
import DreamTeam.GestorTareas.repositories.TaskCollabRepository;
import DreamTeam.GestorTareas.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    TaskCollabRepository taskCollabRepository;

    @Autowired
    ProjectRepository projectRepository;

    //crear tarea
    @PostMapping("/add")
    public ResponseEntity<?> addTask(@RequestBody TaskEntity task){
        try{
            taskRepository.save(task);
            return ResponseEntity.ok("Tarea creada con exito");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }
    }

    //obtener tarea por id
    @GetMapping("/{id}")
    public ResponseEntity<?> findTask(@PathVariable Long id){
        Optional<TaskEntity> taskOpt = taskRepository.findById(id);

        if(taskOpt.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tarea no encontrada");
        } else {
            return ResponseEntity.ok(taskOpt);
        }
    }

    //obtener tareas de un proyecto
    @GetMapping("/project/{id}")
    public ResponseEntity<?> findTaskByProject(@PathVariable Long id){

        Optional<ProjectEntity> projectOpt = projectRepository.findById(id);

        if (projectOpt.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Proyecto no encontrado");
        }

        try{
            List<TaskEntity> tasks = taskRepository.findByProjectId(id);
            return ResponseEntity.ok(tasks);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }

    }


    //actualizar tarea
    @PutMapping("/{id}")
    public ResponseEntity<?> updateTask(@PathVariable Long id, @RequestBody TaskEntity updatedTask){

        Optional<TaskEntity> taskOpt = taskRepository.findById(id);

        if(taskOpt.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tarea no encontrada");
        }

        TaskEntity task = taskOpt.get();

        task.setProject(
                updatedTask.getProject() != null
                ? updatedTask.getProject()
                : task.getProject()
        );

        task.setParentTask(
                updatedTask.getParentTask() != null
                ? updatedTask.getParentTask()
                : task.getParentTask()
        );

        task.setTaskStatus(
                updatedTask.getTaskStatus() != null
                ? updatedTask.getTaskStatus()
                : task.getTaskStatus()
        );

        task.setTaskType(
                updatedTask.getTaskType() != null
                ? updatedTask.getTaskType()
                : task.getTaskType()
        );

        task.setTitle(
                updatedTask.getTitle() != null
                ? updatedTask.getTitle()
                : task.getTitle()
        );

        task.setDescription(
                updatedTask.getDescription() != null
                ? updatedTask.getDescription()
                : task.getDescription()
        );

        task.setUser(
                updatedTask.getUser() != null
                ? updatedTask.getUser()
                : task.getUser()
        );

        task.setPriority(
                updatedTask.getPriority() != null
                ? updatedTask.getPriority()
                : task.getPriority()
        );

        task.setStartDate(
                updatedTask.getStartDate() != null
                ? updatedTask.getStartDate()
                : task.getStartDate()
        );

        task.setEndDate(
                updatedTask.getEndDate() != null
                ? updatedTask.getEndDate()
                : task.getEndDate()
        );

        try{
            taskRepository.save(task);
            return ResponseEntity.ok(task);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }

    }

    //borrar tarea y relaciones
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id){

        Optional<TaskEntity> taskOpt = taskRepository.findById(id);

        if(taskOpt.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tarea no encontrada");
        }

        try {
            //Borrar relacion de colaboradores de tarea
            List<TaskCollabEntity> taskCollabs = taskCollabRepository.findByTaskId(id);
            taskCollabRepository.deleteAll(taskCollabs);

            //Borrar tarea
            taskRepository.delete(taskOpt.get());

            return ResponseEntity.ok("Borrado exitoso");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }

    }

}
