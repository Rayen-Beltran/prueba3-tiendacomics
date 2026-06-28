package com.comics.comic.controller.V1;

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
import com.comics.comic.DTO.ComicDTO;
import com.comics.comic.model.Comic;
import com.comics.comic.service.ComicService;

@RestController
@RequestMapping("/api/v1/comics")
@Slf4j
public class ComicController {
    @Autowired
    private ComicService comicService;

    // Mostrar los comics
    @GetMapping
    public ResponseEntity<List<ComicDTO>> todosLosComics() {
        log.info("Obteniendo todos los cómics");
        List<ComicDTO> comics = comicService.obtenerTodos();
        if (comics.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(comics, HttpStatus.OK);
    }

    // Buscar por Id
    @GetMapping("/{id}")
    public ResponseEntity<ComicDTO> buscarPorId(@PathVariable Integer id) {
        log.info("Buscando cómic con ID: {}", id);
        try {
            ComicDTO comic = comicService.findById(id);
            return new ResponseEntity<>(comic, HttpStatus.OK);
        } catch (RuntimeException e) {
            log.error("Error al buscar cómic con ID: {}: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    // Guardar comic
    @PostMapping
    public ResponseEntity<Comic> guardarComic(@Valid @RequestBody Comic comic) {
        log.info("Guardando cómic: {}", comic.getTitulo());
        Comic nuevoComic = comicService.guardarComic(comic);
        return new ResponseEntity<>(nuevoComic, HttpStatus.CREATED);
    }

    // Actualizar comic
    @PutMapping("/{id}")
    public ResponseEntity<Comic> actualizarComic(@PathVariable Integer id, @Valid @RequestBody Comic comic) {
        log.info("Actualizando cómic con ID: {}", id);
        try {
            Comic comicActualizado = comicService.actualizarComic(id, comic);
            return new ResponseEntity<>(comicActualizado, HttpStatus.OK);
        } catch (RuntimeException e) {
            log.error("Error al actualizar cómic con ID: {}: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar comic
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarComic(@PathVariable Integer id) {
        log.info("Eliminando cómic con ID: {}", id);
        try {
            comicService.eliminarComic(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            log.error("Error al eliminar cómic con ID: {}: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

}
