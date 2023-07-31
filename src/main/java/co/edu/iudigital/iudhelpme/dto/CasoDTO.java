package co.edu.iudigital.iudhelpme.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CasoDTO {
    
    Long id;

    @JsonProperty("fecha_hora")
    @NotNull(message = "campo obligatorio")
    LocalDateTime fechaHora;

    Float latitud;

    Float longitud;

    Float altitud;

    String descripcion;

    Boolean visibilidad;

    @JsonProperty("url_map")
    String urlMap;

    @JsonProperty("rmi_url")
    String rmiUrl;

    @JsonProperty("usuario_id")
    Long usuarioId;

    @JsonProperty("delito_id")
    Long delitoId;
}
