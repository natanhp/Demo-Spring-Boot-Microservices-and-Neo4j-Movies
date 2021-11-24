package id.natanhp.moviesservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collection;

@Data
@AllArgsConstructor
public class RoleDTO {
    private Long id;
    private Collection<String> roles;
    private PersonDTO person;
}
