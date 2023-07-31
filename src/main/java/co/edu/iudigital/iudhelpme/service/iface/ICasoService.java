package co.edu.iudigital.iudhelpme.service.iface;

import java.util.List;

import co.edu.iudigital.iudhelpme.dto.CasoDTO;
import co.edu.iudigital.iudhelpme.exceptions.RestException;
import co.edu.iudigital.iudhelpme.model.Caso;

public interface ICasoService {
    
    List<CasoDTO>consultandoCasos();

    Caso crearCaso(CasoDTO casoDTO) throws RestException;

    //Boolean visibilizarCaso(Boolean visibilizarCaso, Long id);

    CasoDTO consultarPorId(Long id);
}
