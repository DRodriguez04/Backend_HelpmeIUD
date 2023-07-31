package co.edu.iudigital.iudhelpme.dto.response;

import java.time.LocalDate;
import java.util.List;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UsuarioDTO {

    Long id;
    
    String username;

    String nombre;

    String apellido;

    LocalDate fechaNacimiento;

    Boolean enabled;

    String image;

    List<String> roles;
}
