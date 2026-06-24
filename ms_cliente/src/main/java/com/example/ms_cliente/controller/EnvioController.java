package com.example.ms_cliente.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.ms_cliente.DTO.EnvioDTO;
import com.example.ms_cliente.model.Envio;
import com.example.ms_cliente.service.EnvioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/envios")
@Tag(name = "Envíos", description = "Gestión de envíos de pedidos")
public class EnvioController {

    @Autowired
    private EnvioService envioService;

    @Operation(summary = "Listar todos los envíos")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de envíos obtenida"),
        @ApiResponse(responseCode = "204", description = "No hay envíos registrados")
    })
    @GetMapping
    public ResponseEntity<List<EnvioDTO>> todosLosEnvios() {
        log.info("Obteniendo todos los envios");
        List<EnvioDTO> envios = envioService.obtenerTodos();
        if (envios.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(envios, HttpStatus.OK);
    }

    @Operation(summary = "Buscar envío por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Envío encontrado"),
        @ApiResponse(responseCode = "404", description = "Envío no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EnvioDTO> buscarPorId(
            @Parameter(description = "ID del envío") @PathVariable Integer id) {
        log.info("Buscando envio por ID: {}", id);
        try {
            return new ResponseEntity<>(envioService.buscarPorId(id), HttpStatus.OK);
        } catch (RuntimeException e) {
            log.error("Error al buscar envio con ID: {}: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Registrar un nuevo envío")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Envío creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<Envio> agregarEnvio(@Valid @RequestBody Envio envio) {
        log.info("Guardando envio: {}", envio.getIdEnvio());
        try {
            return new ResponseEntity<>(envioService.guardarEnvio(envio), HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error al guardar envio: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Actualizar campos específicos del envío")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Envío actualizado"),
        @ApiResponse(responseCode = "404", description = "Envío no encontrado")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<Envio> editarEnvio(
            @Parameter(description = "ID del envío") @PathVariable Integer id,
            @Valid @RequestBody Envio envio) {
        log.info("Editando envio con ID: {}", id);
        try {
            return new ResponseEntity<>(envioService.actualizarEnvio(id, envio), HttpStatus.OK);
        } catch (RuntimeException e) {
            log.error("Error al editar envio con ID: {}: {}", id, e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Actualizar envío completo")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Envío actualizado"),
        @ApiResponse(responseCode = "404", description = "Envío no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Envio> actualizarEnvio(
            @Parameter(description = "ID del envío") @PathVariable Integer id,
            @Valid @RequestBody Envio envio) {
        log.info("Actualizando envio con ID: {}", id);
        try {
            return new ResponseEntity<>(envioService.actualizarEnvio(id, envio), HttpStatus.OK);
        } catch (RuntimeException e) {
            log.error("Error al actualizar envio con ID: {}: {}", id, e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Eliminar envío por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Envío eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Envío no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarEnvio(
            @Parameter(description = "ID del envío") @PathVariable Integer id) {
        log.info("Eliminando envio con ID: {}", id);
        String resultado = envioService.eliminarEnvio(id);
        if (resultado.contains("exitosamente")) return new ResponseEntity<>(resultado, HttpStatus.OK);
        return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
    }
}