package DreamTeam.GestorTareas.controllers;

import DreamTeam.GestorTareas.dtos.LoginRequestDto;
import DreamTeam.GestorTareas.entities.UsersEntity;
import DreamTeam.GestorTareas.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/users")
@CrossOrigin(origins = "http://localhost:5173")
public class UsersController {

    @Autowired
    private UsersRepository usersRepository;

    @PostMapping("/add")
    public String addUser(@RequestBody UsersEntity usersEntity){
        try {
            usersRepository.save(usersEntity);
            return "Perfecto!";
        } catch (Exception e) {
            return "falle :(";
            //throw new RuntimeException(e);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto loginRequestDto){

        UsersEntity user = usersRepository.findByUsernameAndPassword(
                loginRequestDto.getUsername(),
                loginRequestDto.getPassword()
        );

        if (user != null) {
            return ResponseEntity.ok("Login correcto");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario o contraseña incorrectos");
        }
    }

}
