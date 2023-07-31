package co.edu.iudigital.iudhelpme.dto.request;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DelitoDTORequest {

    @NotEmpty(message = "No puede quedar vacío. Ingrese al menos un caracter")
    String nombre;

    String descripcion; 

    @JsonProperty("usuario_id")
    Long usuarioId;
}
