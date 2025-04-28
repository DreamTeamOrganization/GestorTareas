package DreamTeam.GestorTareas.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenIdUserDto {
    private String token;
    private Long idUser;

    public TokenIdUserDto(String token, Long idUser) {
        this.token = token;
        this.idUser = idUser;
    }
}
