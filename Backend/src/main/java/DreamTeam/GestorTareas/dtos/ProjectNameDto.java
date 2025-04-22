package DreamTeam.GestorTareas.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectNameDto {
    private Long id;
    private String name;

    public ProjectNameDto (Long id, String name){
        this.id = id;
        this.name = name;
    }
}
