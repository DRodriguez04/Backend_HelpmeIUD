package co.edu.iudigital.iudhelpme.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.edu.iudigital.iudhelpme.dto.request.UsuarioDTORequest;
import co.edu.iudigital.iudhelpme.dto.response.UsuarioDTO;
import co.edu.iudigital.iudhelpme.exceptions.RestException;
import co.edu.iudigital.iudhelpme.service.iface.IUsuarioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;

import javax.validation.Valid;

@RestController
@RequestMapping("/usuarios")
@Api(value = "/usuarios", tags = {"Usuarios"})
@SwaggerDefinition(tags = {@Tag(name = "Usuarios", description = "Gestionando Usuarios")})
public class UsuarioController {

    @Autowired
    private IUsuarioService iUsuarioService;

    @ApiOperation(value = "Crea un usuario",
    response = UsuarioDTO.class,
    responseContainer = "Usuario",
    produces = "application/json",
    httpMethod = "POST")
    @PostMapping
    public ResponseEntity<UsuarioDTO> create(@RequestBody @Valid UsuarioDTORequest usuarioDTORequest) throws RestException {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(iUsuarioService.guardarUsuario(usuarioDTORequest));
    }

    @ApiOperation(value = "Lista todos los usuarios",
    responseContainer = "List",
    produces = "application/json",
    httpMethod = "GET")
    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<List<UsuarioDTO>> listadoUsuarios() {
        return ResponseEntity
                .ok()
                .body(iUsuarioService.listarUsuarios());
    }

    @ApiOperation(value = "Consulta un usuario por su ID",
            responseContainer = "List",
            produces = "application/json",
            httpMethod = "GET")
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> consultaPorId(@PathVariable Long id) {
        UsuarioDTO usuarioDTO = iUsuarioService.consultaPorId(id);

        if (usuarioDTO != null) {
            return ResponseEntity.ok(usuarioDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @ApiOperation(value = "Elimina un usuario usando su id",
            responseContainer = "List",
            produces = "application/json",
            httpMethod = "DELETE")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delUsuario(@PathVariable Long id) {
        boolean deleted = iUsuarioService.eliminarPorId(id);
        if (deleted) {
            return new ResponseEntity<String>("Usuario con ID " + id + " borrado satisfactoriamente", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("No se encontró ningún usuario con ID " + id + ".", HttpStatus.NOT_FOUND);
        }
    }

}
