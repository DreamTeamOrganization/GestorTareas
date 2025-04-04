package DreamTeam.GestorTareas.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins="http://localhost:5173")
public class AppController {
    @GetMapping("/hello")
    public String sayHello(){
        return "Hola desde el backend";
    }
}
