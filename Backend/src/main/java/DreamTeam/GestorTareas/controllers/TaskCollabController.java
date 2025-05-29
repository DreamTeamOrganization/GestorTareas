package DreamTeam.GestorTareas.controllers;

import DreamTeam.GestorTareas.dtos.UserSimpleDto;
import DreamTeam.GestorTareas.entities.TaskCollabEntity;
import DreamTeam.GestorTareas.repositories.TaskCollabRepository;
import DreamTeam.GestorTareas.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/collabs")
public class TaskCollabController {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    TaskCollabRepository taskCollabRepository;

    @GetMapping("/{idTask}")
    public ResponseEntity<?> getCollabByProject(@PathVariable Long idTask){
        //Validar que exista la tarea
        if (taskRepository.findById(idTask).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tarea no encontrada");
        };

        //Consultar los usuarios de una tarea
        try{
            List<UserSimpleDto> Users = taskCollabRepository.findUsersByTaskId(idTask);
            return ResponseEntity.ok(Users);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> setCollabByProject(@PathVariable Long idTask, @RequestBody TaskCollabEntity newCollab){
        //Validar que exista la tarea
        if (taskRepository.findById(idTask).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tarea no encontrada");
        };

        try{
            //Guardar nuevo collaborador
            TaskCollabEntity savedTask = taskCollabRepository.save(newCollab);
            return ResponseEntity.ok(savedTask);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }
    }

    @DeleteMapping("/{idCollab}")
    public ResponseEntity<?> deleteCollabByProject(@PathVariable Long idCollab){
        //Validar que exista la tarea
        if (taskCollabRepository.findById(idCollab).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Colaborador no encontrado");
        };

        try{
            //Borrar collaborador
            taskCollabRepository.deleteById(idCollab);
            return ResponseEntity.ok("Colaborador borrado");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }
    }
}
