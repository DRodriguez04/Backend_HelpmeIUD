package co.edu.iudigital.iudhelpme.controller;

import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import co.edu.iudigital.iudhelpme.dto.request.DelitoDTORequest;
import co.edu.iudigital.iudhelpme.dto.response.DelitoDTO;
import co.edu.iudigital.iudhelpme.exceptions.RestException;
import co.edu.iudigital.iudhelpme.service.iface.IDelitoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;

@RestController
@RequestMapping("/delitos")
@Api(value = "/delitos", tags = {"Delitos"})
@SwaggerDefinition(tags = {@Tag(name = "Delitos", description = "Manejando Delitos")})
public class DelitoController {

    @Autowired
    IDelitoService iDelitoService;

    @ApiOperation(value = "Lista todos los delitos",
    responseContainer = "List",
    produces = "application/json",
    httpMethod = "GET")
    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<List<DelitoDTO>> allDelitos() {
        return ResponseEntity
                .ok()
                .body(iDelitoService.consultarTodosDelitos());
    }

    @ApiOperation(value = "Crea un delito",
    response = DelitoDTO.class,
    responseContainer = "Delito",
    produces = "application/json",
    httpMethod = "POST")
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<DelitoDTO> createDelito(@Valid @RequestBody DelitoDTORequest delitoDTORequest)
            throws RestException {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(iDelitoService.guardarDelito(delitoDTORequest));
    }

    @ApiOperation(value = "Elimina un delito por su ID",
            responseContainer = "List",
            produces = "application/json",
            httpMethod = "DELETE")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delDelito(@PathVariable Long id) {
        boolean deleted = iDelitoService.borrarPorId(id);
        if (deleted) {
            return new ResponseEntity<String>("Delito con ID " + id + " borrado satisfactoriamente", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("No se encontró ningún delito con ID " + id + ".", HttpStatus.NOT_FOUND);
        }
    }
    /*
     * @DeleteMapping("/{id}")
     * public void delDelito(@PathVariable Long id) {
     * iDelitoService.borrarPorId(id);
     * 
     * De la manera que lo hacía anteriormente sin usar el objeto
     * ResponseEntity.
     * }
     */

    @ApiOperation(value = "Actualiza un delito",
            response = DelitoDTO.class,
            responseContainer = "Delito",
            produces = "application/json",
            httpMethod = "PUT")
    @PutMapping("/{id}")
    public ResponseEntity<String> actualizarDelito(@PathVariable Long id,
            @RequestBody DelitoDTORequest delitoDTORequest) {
        try {
            iDelitoService.actualizarDelito(id, delitoDTORequest);
            return ResponseEntity.ok("Delito actualizado correctamente.");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encontró ningún delito con el ID proporcionado.");
        }
    }

}
