package co.edu.iudigital.iudhelpme.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import co.edu.iudigital.iudhelpme.dto.CasoDTO;
import co.edu.iudigital.iudhelpme.exceptions.RestException;
import co.edu.iudigital.iudhelpme.model.Caso;
import co.edu.iudigital.iudhelpme.service.iface.ICasoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;

@RestController
@RequestMapping("/casos")
@Api(value = "/casos", tags = {"Casos"})
@SwaggerDefinition(tags = {@Tag(name = "Casos", description = "Manejando Casos")})
public class CasoController {

    @Autowired
    private ICasoService iCasoService;

    @ApiOperation(value = "Lista todos los casos registrados",
    responseContainer = "List",
    produces = "application/json",
    httpMethod = "GET")
    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<List<CasoDTO>> getCasos() {
        return ResponseEntity
                .ok()
                .body(iCasoService.consultandoCasos());
    }

    @ApiOperation(value = "Crea un caso",
    response = Caso.class,
    responseContainer = "Caso",
    produces = "application/json",
    httpMethod = "POST")
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<Caso> create(@RequestBody @Valid CasoDTO casoDTO)
            throws RestException {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(iCasoService.crearCaso(casoDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CasoDTO> consultarCasoPorId(@PathVariable Long id) {
        CasoDTO casoDTO = iCasoService.consultarPorId(id);
        if (casoDTO != null) {
            return ResponseEntity.ok(casoDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
/* 
    public ResponseEntity<String> visibilizarCaso(@PathVariable Long id, @RequestParam Boolean visibilizar) {
        Boolean exito = iCasoService.visibilizarCaso(visibilizar, id);
        if (exito) {
            return new ResponseEntity<>("Caso visibilizado correctamente.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No se pudo visibilizar el caso.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/
}
