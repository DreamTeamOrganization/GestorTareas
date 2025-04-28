package DreamTeam.GestorTareas.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProjectWithUsersDto {
    private Long projectId;
    private String projectName;
    private String projectDescription;
    private List<UserSimpleDto> users;

    public ProjectWithUsersDto(Long projectId, String projectName, String projectDescription, List<UserSimpleDto> users) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.projectDescription = projectDescription;
        this.users = users;
    }
}
