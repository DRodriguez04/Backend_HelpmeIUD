package co.edu.iudigital.iudhelpme.exceptions;

/**
 * Exception de NotFound en el Rest
 * 
 */
public class NotFoundException extends RestException{
    private static final long serialVersionUID = 1L;

    public NotFoundException() {
        super();
    }

    public NotFoundException(ErrorDTO ErrorDTO) {
        super(ErrorDTO);
    }
}
