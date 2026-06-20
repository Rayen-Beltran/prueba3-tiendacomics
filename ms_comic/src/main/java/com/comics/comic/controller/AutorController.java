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
import com.comics.comic.DTO.AutorDTO;
import com.comics.comic.model.Autor;
import com.comics.comic.service.AutorService;

@Slf4j
@RestController
@RequestMapping("/api/v1/autores")
public class AutorController {
    @Autowired
    private AutorService autorService;

    //Mostrar los autores
    @GetMapping
    public ResponseEntity<List<AutorDTO>> todosLosAutores(){
        log.info("Obteniendo todos los autores");
        List<AutorDTO> autores = autorService.obtenerTodos();
        if(autores.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(autores, HttpStatus.OK);
    }
    
    //Buscar por Id
    @GetMapping("/{id}")
    public ResponseEntity<AutorDTO> buscarPorId(@PathVariable Integer id){
        log.info("Buscando autor con ID: {}", id);
        try {
            AutorDTO autor = autorService.buscarPorId(id);
            return new ResponseEntity<>(autor, HttpStatus.OK);
        } catch (RuntimeException e) {
            log.error("Error al buscar autor con ID: {}: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    //Guardar autor
    @PostMapping
    public ResponseEntity<Autor> guardarAutor(@Valid @RequestBody Autor autor){
        log.info("Guardando autor: {}", autor.getNombre());
        Autor nuevoAutor = autorService.guardarAutor(autor);
        return new ResponseEntity<>(nuevoAutor, HttpStatus.CREATED);
    }

    //Actualizar autor
    @PutMapping("/{id}")
    public ResponseEntity<Autor> actualizarAutor(@PathVariable Integer id, @Valid @RequestBody Autor autor){
        log.info("Actualizando autor con ID: {}", id);
        try {
            Autor autorActualizado = autorService.actualizarAutor(id, autor);
            return new ResponseEntity<>(autorActualizado, HttpStatus.OK);
        } catch (RuntimeException e) {
            log.error("Error al actualizar autor con ID: {}: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    //Eliminar autor
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAutor(@PathVariable Integer id){
        log.info("Eliminando autor con ID: {}", id);
        try {
            autorService.eliminarAutor(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            log.error("Error al eliminar autor con ID: {}: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
