package co.edu.iudigital.iudhelpme.service.iface;

import java.util.List;
import java.util.NoSuchElementException;

import co.edu.iudigital.iudhelpme.dto.request.DelitoDTORequest;
import co.edu.iudigital.iudhelpme.dto.response.DelitoDTO;
import co.edu.iudigital.iudhelpme.exceptions.RestException;

public interface IDelitoService {
    
    List<DelitoDTO> consultarTodosDelitos(); //consultar todos los delitos registrados

    DelitoDTO consultarPorId(Long id) throws RestException; //consulta por Id

    DelitoDTO consultarPorNombre(String nombre); // consulta un delito por su nombre

    DelitoDTO guardarDelito(DelitoDTORequest delitoDTORequest) throws RestException; // guardar un delito y retorna en DelitoDTO

    boolean borrarPorId(Long id); //borra por id

    void actualizarDelito(Long id, DelitoDTORequest delitoDTORequest) throws NoSuchElementException;
}
