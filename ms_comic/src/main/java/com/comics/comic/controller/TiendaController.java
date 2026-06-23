package com.comics.comic.controller;

import java.util.List;
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
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import com.comics.comic.DTO.TiendaDTO;
import com.comics.comic.model.Tienda;
import com.comics.comic.service.TiendaService;

@RestController
@RequestMapping("/api/v1/tiendas")
@Slf4j
public class TiendaController {
    @Autowired
    private TiendaService tiendaService;

    //Mostrar las tiendas
    @GetMapping
    public ResponseEntity<List<TiendaDTO>> todasLasTiendas(){
        log.info("Obteniendo todas las tiendas");
        List<TiendaDTO> tiendas = tiendaService.obtenerTodos();
        if(tiendas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(tiendas, HttpStatus.OK);
    }

    //Buscar por Id
    @GetMapping("/{id}")
    public ResponseEntity<TiendaDTO> buscarPorId(@PathVariable Integer id){
        log.info("Buscando tienda con ID: {}", id);
        try {
            TiendaDTO tienda = tiendaService.findById(id);
            return new ResponseEntity<>(tienda, HttpStatus.OK);
        } catch (RuntimeException e) {
            log.error("Error al buscar tienda con ID: {}: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    //Guardar tienda
    @PostMapping
    public ResponseEntity<Tienda> guardarTienda(@Valid @RequestBody Tienda tienda){
        log.info("Guardando tienda: {}", tienda.getNombre_tienda());
        Tienda nuevaTienda = tiendaService.guardarTienda(tienda);
        return new ResponseEntity<>(nuevaTienda, HttpStatus.CREATED);
    }

    //Actualizar tienda
    @PutMapping("/{id}")
    public ResponseEntity<Tienda> actualizarTienda(@PathVariable Integer id, @Valid @RequestBody Tienda tienda){
        log.info("Actualizando tienda con ID: {}", id);
        try {
            Tienda tiendaActualizada = tiendaService.actualizarTienda(id, tienda);
            return new ResponseEntity<>(tiendaActualizada, HttpStatus.OK);
        } catch (RuntimeException e) {
            log.error("Error al actualizar tienda con ID: {}: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    //Eliminar tienda
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTienda(@PathVariable Integer id){
        log.info("Eliminando tienda con ID: {}", id);
        try {
            tiendaService.eliminarTienda(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            log.error("Error al eliminar tienda con ID: {}: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
