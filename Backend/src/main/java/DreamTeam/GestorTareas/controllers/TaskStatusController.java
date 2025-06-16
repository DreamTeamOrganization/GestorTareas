package DreamTeam.GestorTareas.controllers;

import DreamTeam.GestorTareas.entities.TaskStatusEntity;
import DreamTeam.GestorTareas.repositories.TaskStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/taskStatus")
public class TaskStatusController {

    @Autowired
    TaskStatusRepository taskStatusRepository;

    @GetMapping("/{id}")
    public ResponseEntity<?> getTaskStatus (@PathVariable Long id){
        Optional<TaskStatusEntity> taskStatusOpt = taskStatusRepository.findById(id);
        if (taskStatusOpt.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task Status no encontrado");
        } else {
            return ResponseEntity.ok(taskStatusOpt.get());
        }
    }

    @GetMapping("/gatAll")
    public ResponseEntity<?> getTaskStatus (){
        try{
            List<TaskStatusEntity> listTaskStatus = taskStatusRepository.findAll();
            return ResponseEntity.ok(listTaskStatus);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }
    }

}
