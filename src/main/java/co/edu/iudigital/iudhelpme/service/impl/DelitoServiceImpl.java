package co.edu.iudigital.iudhelpme.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.iudigital.iudhelpme.dto.request.DelitoDTORequest;
import co.edu.iudigital.iudhelpme.dto.response.DelitoDTO;
import co.edu.iudigital.iudhelpme.exceptions.BadRequestException;
import co.edu.iudigital.iudhelpme.exceptions.ErrorDTO;
import co.edu.iudigital.iudhelpme.exceptions.RestException;
import co.edu.iudigital.iudhelpme.model.Delito;
import co.edu.iudigital.iudhelpme.model.Usuario;
import co.edu.iudigital.iudhelpme.repository.IDelitoRepository;
import co.edu.iudigital.iudhelpme.repository.IUsuarioRepository;
import co.edu.iudigital.iudhelpme.service.iface.IDelitoService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DelitoServiceImpl implements IDelitoService {

    @Autowired
    private IDelitoRepository iDelitoRepository;

    @Autowired
    private IUsuarioRepository iUsuarioRepository;

    @Override
    @Transactional(readOnly = true)
    public List<DelitoDTO> consultarTodosDelitos() {
        List<Delito> delitos = iDelitoRepository.findAll();
        return delitos.stream()
                .map(delito -> DelitoDTO.builder()
                        .id(delito.getId())
                        .nombre(delito.getNombre())
                        .descripcion(delito.getDescripcion())
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public DelitoDTO consultarPorId(Long id) {
        Optional<Delito> delitoOptional = iDelitoRepository.findById(id);
        if (delitoOptional.isPresent()) {
            Delito delito = delitoOptional.get();
            return DelitoDTO.builder()
                    .id(delito.getId())
                    .nombre(delito.getNombre())
                    .descripcion(delito.getDescripcion())
                    .build();
        }
        log.warn("No existe delito con id {}", id);
        return null;
    }

    @Override
    public DelitoDTO consultarPorNombre(String nombre) {
        List<Delito> delitoOptional = iDelitoRepository.findByNombre(nombre);
        if (!delitoOptional.isEmpty()) {
            Delito delito = delitoOptional.get(0);
            return DelitoDTO.builder()
                    .id(delito.getId())
                    .nombre(delito.getNombre())
                    .descripcion(delito.getDescripcion())
                    .build();
        }
        log.warn("No existe el delito {}", nombre);
        return null;
    }

    @Transactional
    @Override
    public DelitoDTO guardarDelito(DelitoDTORequest delitoDTORequest) throws RestException {
        Delito delito = new Delito();
        delito.setNombre(delitoDTORequest.getNombre());
        delito.setDescripcion(delitoDTORequest.getDescripcion());
        Optional<Usuario> usuarioOptional = iUsuarioRepository.findById(delitoDTORequest.getUsuarioId());
        if (!usuarioOptional.isPresent()) {
            throw new BadRequestException(
                    ErrorDTO.builder()
                            .status(HttpStatus.BAD_REQUEST.value())
                            .message("User does not exist")
                            .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                            .date(LocalDateTime.now())
                            .build());
        }
        delito.setUsuario(usuarioOptional.get());
        delito = iDelitoRepository.save(delito);
        return DelitoDTO.builder()
                .id(delito.getId())
                .nombre(delito.getNombre())
                .descripcion(delito.getDescripcion())
                .build();
    }

    @Override
    public boolean borrarPorId(Long id) {
        Optional<Delito> delitoOptional = iDelitoRepository.findById(id);

        if (delitoOptional.isPresent()) {
            iDelitoRepository.deleteById(id);
            return true; // Indica que el delito se borró correctamente.
        } else {
            return false; // Indica que no se encontró ningún delito con el ID proporcionado.
        }
    }

    @Override
    public void actualizarDelito(Long id, DelitoDTORequest delitoDTORequest) throws NoSuchElementException {
        Optional<Delito> delitoOptional = iDelitoRepository.findById(id);

        if (delitoOptional.isPresent()) {
            Delito delitoExistente = delitoOptional.get();

            // Actualizar las propiedades del delito existente con los datos del DelitoDTO
            delitoExistente.setNombre(delitoDTORequest.getNombre());
            delitoExistente.setDescripcion(delitoDTORequest.getDescripcion());

            // Guardar los cambios en la base de datos
            iDelitoRepository.save(delitoExistente);
        } else {
            // Si no se encuentra el delito con el ID proporcionado, lanzar una excepción
            throw new NoSuchElementException("No se encontró ningún delito con el ID: " + id);
        }
    }

    /*
     * @Override
     * public boolean borrarPorId(Long id) {
     * return iDelitoRepository.deleteById(id);;
     * }
     */

}
