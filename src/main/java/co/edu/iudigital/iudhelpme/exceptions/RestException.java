package co.edu.iudigital.iudhelpme.exceptions;

//Exception del Rest general

public class RestException extends Exception {


    private static final long serialVersionUID = 1L;
    private ErrorDTO ErrorDTO;

    public RestException() {
        super();
    }

    public RestException(ErrorDTO ErrorDTO) {
        super(ErrorDTO.getError());
        this.ErrorDTO = ErrorDTO;
    }

    public RestException(String msg) {
        super(msg);
    }

    public RestException(String msg, Exception ex) {
        super(msg, ex);
    }

    /**
     * @return the ErrorDTO
     */
    public ErrorDTO getErrorDTO() {
        return ErrorDTO;
    }

    /**
     * @param ErrorDTO the ErrorDTO to set
     */
    public void setErrorDTO(ErrorDTO ErrorDTO) {
        this.ErrorDTO = ErrorDTO;
    }
}
