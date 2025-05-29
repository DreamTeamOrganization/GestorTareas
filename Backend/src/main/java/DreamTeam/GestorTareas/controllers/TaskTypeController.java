package DreamTeam.GestorTareas.controllers;

import DreamTeam.GestorTareas.entities.TaskTypeEntity;
import DreamTeam.GestorTareas.repositories.TaskTypeRepository;
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
@RequestMapping("/api/taskType")
public class TaskTypeController {

    @Autowired
    TaskTypeRepository taskTypeRepository;

    @GetMapping("/{id}")
    public ResponseEntity<?> getTaskType (@PathVariable Long id){
        Optional<TaskTypeEntity> taskTypeOpt = taskTypeRepository.findById(id);
        if (taskTypeOpt.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task Type no encontrado");
        } else {
            return ResponseEntity.ok(taskTypeOpt.get());
        }
    }

    @GetMapping("/gatAll")
    public ResponseEntity<?> getTaskType (){
        try{
            List<TaskTypeEntity> listTaskTypes = taskTypeRepository.findAll();
            return ResponseEntity.ok(listTaskTypes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }
    }
}
