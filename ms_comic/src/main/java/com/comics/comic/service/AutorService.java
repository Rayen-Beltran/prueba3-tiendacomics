package com.comics.comic.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import com.comics.comic.DTO.AutorDTO;
import com.comics.comic.model.Autor;
import com.comics.comic.repository.AutorRepository;

@Service
@Transactional
@Slf4j
public class AutorService {
    @Autowired
    private AutorRepository autorRepository;

    public List<AutorDTO> obtenerTodos() {
        log.info("Obteniendo todos los autores");
        return autorRepository.findAll().stream()
                 .map(this::convertirADTO)
                 .toList();
    }

    private AutorDTO convertirADTO(Autor autor) {
        AutorDTO dto = new AutorDTO();
        dto.setId_autor(autor.getId_autor());
        dto.setNombre(autor.getNombre());
        return dto;
    }

    //Guardar autor
    public Autor guardarAutor(Autor autor){
        log.info("Guardando autor: {}", autor.getNombre());
        return autorRepository.save(autor);
    }
    
    //Actualizar autor
    public Autor actualizarAutor(Integer id, Autor autor){
        log.info("Actualizando autor: {}", autor.getNombre());
        Autor autor1 = autorRepository.findById(id).orElseThrow(() -> new RuntimeException("¡El Autor no existe en los registros!"));
        
        if(autor.getNombre() != null){
            autor1.setNombre(autor.getNombre());
        }
        return autorRepository.save(autor1);
    }

    //Eliminar autor
    public void eliminarAutor(Integer id){
        log.info("Eliminando autor con ID: {}", id);
        Autor autor = autorRepository.findById(id).orElseThrow(() -> new RuntimeException("¡El Autor no existe en los registros!"));
        autorRepository.delete(autor);
    }

    //buscar por id
    public AutorDTO buscarPorId(Integer id){
        log.info("Buscando autor con ID: {}", id);
        Autor autor = autorRepository.findById(id).orElseThrow(() -> new RuntimeException("¡El Autor no existe en los registros!"));
        return convertirADTO(autor);
    }
}
