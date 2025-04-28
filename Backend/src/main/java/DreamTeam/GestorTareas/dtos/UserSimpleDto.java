package DreamTeam.GestorTareas.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSimpleDto {
    private Long id;
    private String username;

    public UserSimpleDto (Long id, String username){
        this.id = id;
        this.username = username;
    }
}
