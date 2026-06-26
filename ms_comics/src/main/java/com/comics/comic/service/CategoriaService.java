package com.comics.comic.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import com.comics.comic.DTO.CategoriaDTO;
import com.comics.comic.model.Categoria;
import com.comics.comic.repository.CategoriaRepository;

@Service
@Transactional
@Slf4j
public class CategoriaService {
    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<CategoriaDTO> obtenerTodos() {
        log.info("Obteniendo todas las categorías");
        return categoriaRepository.findAll().stream()
                 .map(this::convertirADTO)
                 .toList();
    }

    private CategoriaDTO convertirADTO(Categoria categoria) {
        CategoriaDTO dto = new CategoriaDTO();
        dto.setId_categoria(categoria.getId_categoria());
        dto.setNombre(categoria.getNombre());
        return dto;
    }

    //Guardar categoria
    public Categoria guardarCategoria(Categoria categoria){
        log.info("Guardando categoría: {}", categoria.getNombre());
        return categoriaRepository.save(categoria);
    }

    //Actualizar categoria
    public Categoria actualizarCategoria(Integer id, Categoria categoria){
        log.info("Actualizando categoría: {}", categoria.getNombre());
        Categoria categoria1 = categoriaRepository.findById(id).orElseThrow(() -> new RuntimeException("¡La Categoría no existe en los registros!"));
        
        if(categoria.getNombre() != null){
            categoria1.setNombre(categoria.getNombre());
        }
        return categoriaRepository.save(categoria1);
    }

    //Eliminar categoria
    public void eliminarCategoria(Integer id){
        log.info("Eliminando categoría con ID: {}", id);
        Categoria categoria = categoriaRepository.findById(id).orElseThrow(() -> new RuntimeException("¡La Categoría no existe en los registros!"));
        categoriaRepository.delete(categoria);
    }

    //buscar por id
    public CategoriaDTO buscarPorId(Integer id){
        log.info("Buscando categoría con ID: {}", id);
        Categoria categoria = categoriaRepository.findById(id).orElseThrow(() -> new RuntimeException("¡La Categoría no existe en los registros!"));
        return convertirADTO(categoria);
    }
}
