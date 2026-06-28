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
import com.comics.comic.DTO.EditorialDTO;
import com.comics.comic.model.Editorial;
import com.comics.comic.service.EditorialService;

@RestController
@RequestMapping("/api/v1/editoriales")
@Slf4j
public class EditorialController {
    @Autowired
    private EditorialService editorialService;
    //Mostrar las editoriales
    @GetMapping
    public ResponseEntity<List<EditorialDTO>> todasLasEditoriales(){
        log.info("Obteniendo todas las editoriales");
        List<EditorialDTO> editoriales = editorialService.obtenerTodos();
        if(editoriales.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(editoriales, HttpStatus.OK);
    }

    //Buscar por Id
    @GetMapping("/{id}")
    public ResponseEntity<EditorialDTO> buscarPorId(@PathVariable Integer id){
        log.info("Buscando editorial con ID: {}", id);
        try {
            EditorialDTO editorial = editorialService.buscarPorId(id);
            return new ResponseEntity<>(editorial, HttpStatus.OK);
        } catch (RuntimeException e) {
            log.error("Error al buscar editorial con ID: {}: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    //Guardar editorial
    @PostMapping
    public ResponseEntity<Editorial> guardarEditorial(@Valid @RequestBody Editorial editorial){
        log.info("Guardando editorial: {}", editorial.getNombre());
        Editorial nuevaEditorial = editorialService.guardarEditorial(editorial);
        return new ResponseEntity<>(nuevaEditorial, HttpStatus.CREATED);
    }

    //Actualizar editorial
    @PutMapping("/{id}")
    public ResponseEntity<Editorial> actualizarEditorial(@PathVariable Integer id, @Valid @RequestBody Editorial editorial){
        log.info("Actualizando editorial con ID: {}", id);
        try {
            Editorial editorialActualizada = editorialService.actualizarEditorial(id, editorial);
            return new ResponseEntity<>(editorialActualizada, HttpStatus.OK);
        } catch (RuntimeException e) {
            log.error("Error al actualizar editorial con ID: {}: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    //Eliminar editorial
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEditorial(@PathVariable Integer id){
        log.info("Eliminando editorial con ID: {}", id);
        try {
            editorialService.eliminarEditorial(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            log.error("Error al eliminar editorial con ID: {}: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

}
