package co.edu.iudigital.iudhelpme.exceptions;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Error Dto
 * 
 */
@Getter
@Setter
@Builder
public class ErrorDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String error;

    private String message;

    private int status;

    private LocalDateTime date;

    public static ErrorDTO obtenErrorDTO(String error, String message, int status) {
        ErrorDTO errorDTO = new ErrorDTO(error, message, status, null);
        errorDTO.setError(error);
        errorDTO.setMessage(message);
        errorDTO.setStatus(status);
        errorDTO.setDate(LocalDateTime.now());
        return errorDTO;
    
    }

    /**
     * Obtiene nuevo error
     *    
     * @param error String Nombre error HTTP
     * @param messag   String Mensaje personalizado del error HTTP
     * @param status int Codigo error HTTP
     * @return errorDTO
     */

}
