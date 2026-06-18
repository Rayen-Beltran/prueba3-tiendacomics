package com.example.ms_cliente.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ms_cliente.DTO.PagoDTO;
import com.example.ms_cliente.model.Pago;
import com.example.ms_cliente.service.PagoService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/pago")
public class PagoController {
    @Autowired
    private PagoService pagoService;

    @GetMapping("/{id}")
    public ResponseEntity<PagoDTO> obtenerPagoPorId(@PathVariable Integer id) {
        log.info("Obteniendo pago con ID: {}", id);
        try {
            PagoDTO pagoDTO = pagoService.buscarPorId(id);
            return new ResponseEntity<>(pagoDTO, HttpStatus.OK);
        } catch (RuntimeException e) {
            log.error("Error al obtener pago con ID: {}: {}", id, e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/guardar")
    public ResponseEntity<Pago> guardarPago(@Valid @RequestBody Pago pago) {
        log.info("Guardando nuevo pago: {}", pago);
        Pago nuevoPago = pagoService.guardarPago(pago);
        return new ResponseEntity<>(nuevoPago, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Pago> editarPago(@PathVariable Integer id, @Valid @RequestBody Pago pago) {
        log.info("Editando pago con ID: {}", id);
        try {
            Pago editado = pagoService.guardarPago(pago);
            return new ResponseEntity<>(editado, HttpStatus.OK);
        } catch (RuntimeException e) {
            log.error("Error al editar el Pago con ID: {}: {}", id, e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Pago> actualizarPago(@PathVariable Integer id, @Valid @RequestBody Pago pagoActualizado) {
        log.info("Actualizando pago con ID: {}", id);
        try {
            Pago pago = pagoService.actualizarPago(id, pagoActualizado);
            return new ResponseEntity<>(pago, HttpStatus.OK);
        } catch (RuntimeException e) {
            log.error("Error al actualizar pago con ID: {}: {}", id, e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarPago(@PathVariable Integer id) {
        log.info("Eliminando pago con ID: {}", id);
        String resultado = pagoService.eliminar(id);
        if (resultado.contains("exitosamente")) {
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
        }
    }
}
