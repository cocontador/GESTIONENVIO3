package com.Envios.gestionEnvio.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Envios.gestionEnvio.Assemblers.EnvioModelAssembler;
import com.Envios.gestionEnvio.Model.Envio;
import com.Envios.gestionEnvio.Service.EnvioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/envios")
public class EnvioController {

@Autowired
private EnvioService envioService;
@Autowired
private EnvioModelAssembler assembler;
@Operation(summary = "Crea un nuevo envío")
@ApiResponses(value = {
@ApiResponse(responseCode = "201", description = "Envío creado exitosamente",
content = @Content(mediaType = "application/json",
schema = @Schema(implementation = Envio.class))),
@ApiResponse(responseCode = "400", description = "Solicitud inválida")
})
@PostMapping
public Envio crearEnvio(@RequestBody Envio envio) {
        return envioService.crearEnvio(envio);
}

@Operation(summary = "Obtiene la lista de todos los envíos")
@ApiResponse(responseCode = "200", description = "Lista de envíos obtenida exitosamente",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = Envio.class)))
@GetMapping
public List<Envio> obtenerTodos() {
        return envioService.obtenerTodos();
}

@Operation(summary = "Obtiene un envío por su ID")
@ApiResponses(value = {
@ApiResponse(responseCode = "200", description = "Envío encontrado",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = Envio.class))),
@ApiResponse(responseCode = "404", description = "Envío no encontrado")
})
@GetMapping("/{id}")
public EntityModel<Envio> obtenerPorId(@PathVariable Long id) {
        Envio envio= envioService.obtenerPorId(id);
        return assembler.toModel(envio); // Busca un producto por su ID
}


@Operation(summary = "Actualiza un envío existente")
@ApiResponses(value = {
@ApiResponse(responseCode = "200", description = "Envío actualizado exitosamente",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = Envio.class))),
@ApiResponse(responseCode = "404", description = "Envío no encontrado"),
@ApiResponse(responseCode = "400", description = "Solicitud inválida")
})
@PutMapping("/{id}")
public Envio actualizarEnvio(@PathVariable Long id, @RequestBody Envio envio) {
        return envioService.actualizarEnvio(id, envio);
}

@Operation(summary = "Elimina un envío por su ID")
@ApiResponses(value = {
@ApiResponse(responseCode = "204", description = "Envío eliminado exitosamente"),
@ApiResponse(responseCode = "404", description = "Envío no encontrado")
})
@DeleteMapping("/{id}")
public ResponseEntity<Void> eliminarEnvio(@PathVariable Long id) {
        envioService.eliminarEnvio(id);
return ResponseEntity.noContent().build();
}
}

