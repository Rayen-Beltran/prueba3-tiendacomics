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
import com.comics.comic.DTO.CategoriaDTO;
import com.comics.comic.model.Categoria;
import com.comics.comic.service.CategoriaService;

@RestController
@RequestMapping("/api/v1/categorias")
@Slf4j
public class CategoriaController {
    @Autowired
    private CategoriaService categoriaService;

    //Mostrar las categorias
    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> todasLasCategorias(){
        log.info("Obteniendo todas las categorías");
        List<CategoriaDTO> categorias = categoriaService.obtenerTodos();
        if(categorias.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(categorias, HttpStatus.OK);
    }

    //Buscar por Id
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTO> buscarPorId(@PathVariable Integer id){
        log.info("Buscando categoría con ID: {}", id);
        try {
            CategoriaDTO categoria = categoriaService.buscarPorId(id);
            return new ResponseEntity<>(categoria, HttpStatus.OK);
        } catch (RuntimeException e) {
            log.error("Error al buscar categoría con ID: {}: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    //Guardar categoria
    @PostMapping
    public ResponseEntity<Categoria> guardarCategoria(@Valid @RequestBody Categoria categoria){
        log.info("Guardando categoría: {}", categoria.getNombre());
        Categoria nuevaCategoria = categoriaService.guardarCategoria(categoria);
        return new ResponseEntity<>(nuevaCategoria, HttpStatus.CREATED);
    }

    //Actualizar categoria
    @PutMapping("/{id}")
    public ResponseEntity<Categoria> actualizarCategoria(@PathVariable Integer id, @Valid @RequestBody Categoria categoria){
        log.info("Actualizando categoría con ID: {}", id);
        try {
            Categoria categoriaActualizada = categoriaService.actualizarCategoria(id, categoria);
            return new ResponseEntity<>(categoriaActualizada, HttpStatus.OK);
        } catch (RuntimeException e) {
            log.error("Error al actualizar categoría con ID: {}: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    //Eliminar categoria
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCategoria(@PathVariable Integer id){
        log.info("Eliminando categoría con ID: {}", id);
        try {
            categoriaService.eliminarCategoria(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            log.error("Error al eliminar categoría con ID: {}: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
