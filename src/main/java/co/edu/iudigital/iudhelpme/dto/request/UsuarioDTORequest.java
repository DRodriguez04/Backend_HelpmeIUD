package co.edu.iudigital.iudhelpme.dto.request;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UsuarioDTORequest {
    
    @NotNull(message = "campo obligatorio")
    @Email(message = "No cumple con formato Email")
    String username;

    @NotNull(message = "campo obligatorio")
    String nombre;

    String apellido;

    @Size(min = 5, message = "minimo 5 caracteres")
    String password;

    @JsonProperty("fecha_nacimiento")
    LocalDate fechaNacimiento;

    Boolean enabled;

    String image;
}
