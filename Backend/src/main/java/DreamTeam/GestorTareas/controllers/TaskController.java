package DreamTeam.GestorTareas.controllers;

import DreamTeam.GestorTareas.dtos.TaskSimpleDto;
import DreamTeam.GestorTareas.entities.*;
import DreamTeam.GestorTareas.repositories.*;
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

    @Autowired
    TaskStatusRepository taskStatusRepository;

    @Autowired
    TaskTypeRepository taskTypeRepository;

    @Autowired
    UsersRepository usersRepository;

    //crear tarea
    @PostMapping("/add")
    public ResponseEntity<?> addTask(@RequestBody TaskSimpleDto taskSimple){

        TaskEntity task = new TaskEntity();

        //corroborar informacion
        ProjectEntity project = projectRepository.findById(taskSimple.getProject())
                .orElseThrow(() -> new RuntimeException("Projecto no encontrado"));

        if (taskSimple.getParentTask() != null){
            TaskEntity parentTask = taskRepository.findById(taskSimple.getParentTask())
                    .orElseThrow(() -> new RuntimeException("Parent task no encontrada"));

            task.setParentTask(parentTask);
        };

        TaskStatusEntity taskStatus = taskStatusRepository.findById(taskSimple.getTaskStatus())
                .orElseThrow(() -> new RuntimeException("Task status no encontrado"));

        TaskTypeEntity taskType = taskTypeRepository.findById(taskSimple.getTaskType())
                .orElseThrow(() -> new RuntimeException("Task type no encontrado"));

        UserEntity user = usersRepository.findById(taskSimple.getUser())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        task.setProject(project);
        task.setUser(user);
        task.setTaskStatus(taskStatus);
        task.setTaskType(taskType);
        task.setTitle(taskSimple.getTitle());
        task.setDescription(taskSimple.getDescription());
        task.setPriority(taskSimple.getPriority());
        task.setStartDate(taskSimple.getStartDate());
        task.setEndDate(taskSimple.getEndDate());

        try{
            taskRepository.save(task);
            return ResponseEntity.ok(task);
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
    public ResponseEntity<?> updateTask(@PathVariable Long id, @RequestBody TaskSimpleDto updatedTask){

        Optional<TaskEntity> taskOpt = taskRepository.findById(id);

        if(taskOpt.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tarea no encontrada");
        }

        TaskEntity task = taskOpt.get();

        if (updatedTask.getProject() != null) {
            ProjectEntity project = projectRepository.findById(updatedTask.getProject())
                    .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));
            task.setProject(project);
        }

        if (updatedTask.getParentTask() != null){
            TaskEntity parentTask = taskRepository.findById(updatedTask.getParentTask())
                    .orElseThrow(()-> new RuntimeException("Parent task no encontrado"));
            task.setParentTask(parentTask);
        }

        if (updatedTask.getTaskStatus() != null){
            TaskStatusEntity taskStatus = taskStatusRepository.findById(updatedTask.getTaskStatus())
                    .orElseThrow(()->new RuntimeException("Task status no encontrado"));
            task.setTaskStatus(taskStatus);
        }

        if (updatedTask.getTaskType() != null){
            TaskTypeEntity taskType = taskTypeRepository.findById(updatedTask.getTaskType())
                    .orElseThrow(()->new RuntimeException("Task type no encontrado"));
            task.setTaskType(taskType);
        }

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

        if (updatedTask.getUser() != null){
            UserEntity user = usersRepository.findById(updatedTask.getUser())
                    .orElseThrow(() -> new RuntimeException("User no encontrado"));
            task.setUser(user);
        }

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
