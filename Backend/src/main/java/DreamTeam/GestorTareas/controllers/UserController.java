package DreamTeam.GestorTareas.controllers;

import DreamTeam.GestorTareas.dtos.LoginRequestDto;
import DreamTeam.GestorTareas.dtos.TokenIdUserDto;
import DreamTeam.GestorTareas.entities.UserEntity;
import DreamTeam.GestorTareas.repositories.UsersRepository;
import DreamTeam.GestorTareas.services.JwtUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("api/users")
public class UserController {

    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/add")
    public ResponseEntity<String> addUser(@RequestBody UserEntity userEntity){

        UserEntity user = usersRepository.findByUsername(userEntity.getUsername());

        if (user == null){
            //el usuario no existe
            try {
                String hashedPassword = passwordEncoder.encode(userEntity.getPassword());
                userEntity.setPassword(hashedPassword);
                usersRepository.save(userEntity);
                return ResponseEntity.ok("Usuario registrado y cifrado correctamente");
            } catch (Exception e) {
                return ResponseEntity.badRequest().body("Fallo en el registro: \n" + e.getMessage());
                //throw new RuntimeException(e);
            }
        } else{
            //el usuario existe
            return ResponseEntity.badRequest().body("Usuario ya utilizado");
        }
    }

    private final String secretKey = "mimegaclaveultrasecreta123456";

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto){

        UserEntity user = usersRepository.findByUsername(loginRequestDto.getUsername());

        if (user != null && passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            //se encontro el usuario y contraseña
            String token = jwtUtil.generarToken(user.getUsername());

            TokenIdUserDto tokenIdUser = new TokenIdUserDto(
                    token,
                    user.getId()
            );

            return ResponseEntity.ok(tokenIdUser);
        } else {
            //usuario y contraseña no coinciden
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario o contraseña incorrectos");
        }
    }

}
