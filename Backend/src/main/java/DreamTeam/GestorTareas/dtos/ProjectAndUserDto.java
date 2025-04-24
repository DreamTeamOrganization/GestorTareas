package DreamTeam.GestorTareas.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectAndUserDto {
    private String name;
    private String description;
    private Long idUser;

    public ProjectAndUserDto( String name, String description, Long idUser){
        this.name = name;
        this.description = description;
        this.idUser = idUser;
    }
}
