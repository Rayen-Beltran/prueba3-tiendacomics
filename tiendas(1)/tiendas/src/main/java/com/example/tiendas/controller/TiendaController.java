package com.example.tiendas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tiendas.DTO.TiendaDTO;
import com.example.tiendas.model.Tienda;
import com.example.tiendas.service.TiendaService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/tiendas")

public class TiendaController {

    @Autowired
    private TiendaService tiendaService;

    @GetMapping
    public ResponseEntity<List<TiendaDTO>> todosLosEmpleados(){
        log.info("Obteniendo todas las tiendas");
        List<TiendaDTO> tienda = tiendaService.obtenerTodas();
        if(tienda.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(tienda, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Tienda> agregarTienda(@Valid @RequestBody Tienda tienda) {
        log.info("Agregando nueva Tienda: {}", tienda);
        try {
            Tienda guardado = tiendaService.guardarTienda(tienda);
            return new ResponseEntity<>(guardado, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error al agregar la Tienda: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Tienda> editarTienda(@PathVariable Integer id, @Valid @RequestBody Tienda tienda) {
        log.info("Editando tienda con ID: {}", id);
        try {
            Tienda editado = tiendaService.guardarTienda(tienda);
            return new ResponseEntity<>(editado, HttpStatus.OK);
        } catch (RuntimeException e) {
            log.error("Error al editar la tienda con ID: {}: {}", id, e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tienda> actualizarTienda(@PathVariable Integer id, @Valid @RequestBody Tienda tienda){
        log.info("Actualizando tienda con ID: {}", id);
        try{
            Tienda newTienda = tiendaService.actualizarTienda( id, tienda);
            return new ResponseEntity<>(newTienda, HttpStatus.OK);
        }catch (RuntimeException e) {
            log.error("Error al actualizar la tienda con ID: {}: {}", id, e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarTienda(@PathVariable Integer id) {
        log.info("Eliminando tienda con ID: {}", id);
        String resultado = tiendaService.eliminarTienda(id);
        if (resultado.contains("exitosamente")) {
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<TiendaDTO> buscarPorId(@PathVariable Integer id){
        log.info("Buscando tienda con ID: {}", id);
        try {
            TiendaDTO tienda = tiendaService.buscarPorId(id);
            return new ResponseEntity<>(tienda, HttpStatus.OK);
        } catch (RuntimeException e) {
            log.error("Error al buscar la tienda con ID: {}: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

}
