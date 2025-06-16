package DreamTeam.GestorTareas.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDto {
    private String username;
    private String password;

    public LoginRequestDto( String username, String password){
        this.username = username;
        this.password = password;
    }
}
