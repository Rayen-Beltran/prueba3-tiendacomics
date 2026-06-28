package com.example.ms_cliente.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.ms_cliente.DTO.PagoDTO;
import com.example.ms_cliente.model.Pago;
import com.example.ms_cliente.service.PagoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/pago")
@Tag(name = "Pagos", description = "Gestión de pagos de la tienda de comics")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @Operation(summary = "Buscar pago por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Pago encontrado"),
        @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PagoDTO> obtenerPagoPorId(
            @Parameter(description = "ID del pago") @PathVariable Integer id) {
        log.info("Obteniendo pago con ID: {}", id);
        try {
            return new ResponseEntity<>(pagoService.buscarPorId(id), HttpStatus.OK);
        } catch (RuntimeException e) {
            log.error("Error al obtener pago con ID: {}: {}", id, e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Registrar un nuevo pago")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Pago creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping("/guardar")
    public ResponseEntity<Pago> guardarPago(@Valid @RequestBody Pago pago) {
        log.info("Guardando nuevo pago: {}", pago);
        return new ResponseEntity<>(pagoService.guardarPago(pago), HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar campos específicos del pago")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Pago actualizado"),
        @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<Pago> editarPago(
            @Parameter(description = "ID del pago") @PathVariable Integer id,
            @Valid @RequestBody Pago pago) {
        log.info("Editando pago con ID: {}", id);
        try {
            return new ResponseEntity<>(pagoService.guardarPago(pago), HttpStatus.OK);
        } catch (RuntimeException e) {
            log.error("Error al editar el Pago con ID: {}: {}", id, e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Actualizar pago completo")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Pago actualizado"),
        @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    })
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Pago> actualizarPago(
            @Parameter(description = "ID del pago") @PathVariable Integer id,
            @Valid @RequestBody Pago pagoActualizado) {
        log.info("Actualizando pago con ID: {}", id);
        try {
            return new ResponseEntity<>(pagoService.actualizarPago(id, pagoActualizado), HttpStatus.OK);
        } catch (RuntimeException e) {
            log.error("Error al actualizar pago con ID: {}: {}", id, e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Eliminar pago por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Pago eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarPago(
            @Parameter(description = "ID del pago") @PathVariable Integer id) {
        log.info("Eliminando pago con ID: {}", id);
        String resultado = pagoService.eliminar(id);
        if (resultado.contains("exitosamente")) return new ResponseEntity<>(resultado, HttpStatus.OK);
        return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
    }
}