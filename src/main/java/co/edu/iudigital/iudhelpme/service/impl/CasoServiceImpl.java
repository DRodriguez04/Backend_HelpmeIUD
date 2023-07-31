package co.edu.iudigital.iudhelpme.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.iudigital.iudhelpme.dto.CasoDTO;
import co.edu.iudigital.iudhelpme.exceptions.BadRequestException;
import co.edu.iudigital.iudhelpme.exceptions.ErrorDTO;
import co.edu.iudigital.iudhelpme.exceptions.RestException;
import co.edu.iudigital.iudhelpme.model.Caso;
import co.edu.iudigital.iudhelpme.model.Delito;
import co.edu.iudigital.iudhelpme.model.Usuario;
import co.edu.iudigital.iudhelpme.repository.ICasoRepository;
import co.edu.iudigital.iudhelpme.repository.IDelitoRepository;
import co.edu.iudigital.iudhelpme.repository.IUsuarioRepository;
import co.edu.iudigital.iudhelpme.service.iface.ICasoService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CasoServiceImpl implements ICasoService {

    @Autowired
    private ICasoRepository iCasoRepository;

    @Autowired
    private IUsuarioRepository iUsuarioRepository;

    @Autowired
    private IDelitoRepository iDelitoRepository;

    @Transactional(readOnly = true)
    @Override
    public List<CasoDTO> consultandoCasos() {
        log.info("buscando casos registrados en sistema{}");
        List<Caso> casos = iCasoRepository.findAll();
        return casos.stream().map(caso -> 
            CasoDTO.builder()
                    .id(caso.getId())
                    .descripcion(caso.getDescription())
                    .altitud(caso.getAltitud())
                    .longitud(caso.getLongitud())
                    .latitud(caso.getLatitud())
                    .visibilidad(caso.getVisibilidad())
                    .fechaHora(caso.getFechaHora())
                    .urlMap(caso.getUrlMap())
                    .rmiUrl(caso.getRmiUrl())
                    .usuarioId(caso.getUsuario().getId())
                    .delitoId(caso.getDelito().getId())
                    .build()
                    ).collect(Collectors.toList());

    }

    @Transactional
    @Override
    public Caso crearCaso(CasoDTO casoDTO) throws RestException {
        Optional<Usuario> usuario = iUsuarioRepository.findById(casoDTO.getUsuarioId());
        Optional<Delito> delito = iDelitoRepository.findById(casoDTO.getDelitoId());
        if (!usuario.isPresent() || !delito.isPresent()) {
            log.error("No existe el usuario {}" , casoDTO.getUsuarioId());
            throw new BadRequestException(ErrorDTO.builder()
                                                .status(HttpStatus.BAD_REQUEST.value())
                                                .message("User does not exist")
                                                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                                                .date(LocalDateTime.now())
                                                .build());
                                                
        }
        Caso caso = new Caso();
        caso.setFechaHora(casoDTO.getFechaHora());
        caso.setLatitud(casoDTO.getLatitud());
        caso.setLongitud(casoDTO.getLongitud());
        caso.setAltitud(casoDTO.getAltitud());
        caso.setDescription(casoDTO.getDescripcion());
        caso.setVisibilidad(true);
        caso.setRmiUrl(casoDTO.getRmiUrl());
        caso.setUrlMap(casoDTO.getUrlMap());
        caso.setUsuario(usuario.get());
        caso.setDelito(delito.get());
        return iCasoRepository.save(caso);
    }

    /* 
    @Transactional(readOnly = true)
    @Override
    public Boolean visibilizarCaso(Boolean visibilizarCaso, Long id) {
        return iCasoRepository.setVisibilidad(visibilizarCaso, id);
    }*/

    @Transactional(readOnly = true)
    @Override
    public CasoDTO consultarPorId(Long id) {
        Optional<Caso> casoOptional = iCasoRepository.findById(id);
        if (casoOptional.isPresent()) {
            Caso caso = casoOptional.get();
            return CasoDTO.builder()
                    .id(caso.getId())
                    .descripcion(caso.getDescription())
                    .altitud(caso.getAltitud())
                    .latitud(caso.getLatitud())
                    .longitud(caso.getLongitud())
                    .visibilidad(caso.getVisibilidad())
                    .rmiUrl(caso.getRmiUrl())
                    .urlMap(caso.getUrlMap())
                    .usuarioId(caso.getUsuario().getId())
                    .delitoId(caso.getDelito().getId())
                    .build();
        }
        log.warn("No existe caso con ID {}"+ id + " proporcionado");;
        return null;
    }

}
