package DreamTeam.GestorTareas.controllers;

import DreamTeam.GestorTareas.dtos.LoginRequestDto;
import DreamTeam.GestorTareas.entities.UserEntity;
import DreamTeam.GestorTareas.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/users")
public class UsersController {

    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/add")
    public String addUser(@RequestBody UserEntity userEntity){
        try {
            String hashedPassword = passwordEncoder.encode(userEntity.getPassword());
            userEntity.setPassword(hashedPassword);
            usersRepository.save(userEntity);
            return "Usuario registrado y cifrado!";
        } catch (Exception e) {
            return "Falle en el registro:(";
            //throw new RuntimeException(e);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto loginRequestDto){

        UserEntity user = usersRepository.findByUsername(loginRequestDto.getUsername());

        if (user != null && passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            return ResponseEntity.ok("Login correcto");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario o contraseña incorrectos");
        }
    }

}
