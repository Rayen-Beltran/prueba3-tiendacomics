package com.comics.comic.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import com.comics.comic.DTO.ComicDTO;
import com.comics.comic.model.Comic;
import com.comics.comic.repository.ComicRepository;

@Service
@Transactional
@Slf4j
public class ComicService {
    @Autowired
    private ComicRepository comicRepository;

    public List<ComicDTO> obtenerTodos() {
        log.info("Obteniendo todos los cómics");
        return comicRepository.findAll().stream()
                 .map(this::convertirADTO)
                 .toList();
    }
    
    private ComicDTO convertirADTO(Comic comic) {
        ComicDTO dto = new ComicDTO();
        dto.setId_comic(comic.getId());
        dto.setTitulo(comic.getTitulo());
        return dto;
    }

    //Guardar comic
    public Comic guardarComic(Comic comic){
        log.info("Guardando cómic: {}", comic.getTitulo());
        return comicRepository.save(comic);
    }

    //Actualizar comic
    public Comic actualizarComic(Long id, Comic comic){
        log.info("Actualizando cómic: {}", comic.getTitulo());
        Comic comic1 = comicRepository.findById(id).orElseThrow(() -> new RuntimeException("¡El Cómic no existe en los registros!"));
        
        if(comic.getTitulo() != null){
            comic1.setTitulo(comic.getTitulo());
        }
        if(comic.getAutores() != null){
            comic1.setAutores(comic.getAutores());
        }
        if(comic.getEditoriales() != null){
            comic1.setEditoriales(comic.getEditoriales());
        }
        if(comic.getCategorias() != null){
            comic1.setCategorias(comic.getCategorias());
        }
        return comicRepository.save(comic1);
    }

    //Eliminar comic
    public void eliminarComic(Long id){
        log.info("Eliminando cómic con ID: {}", id);
        Comic comic = comicRepository.findById(id).orElseThrow(() -> new RuntimeException("¡El Cómic no existe en los registros!"));
        comicRepository.delete(comic);
    }

    //buscar por id
    public ComicDTO buscarPorId(Long id){
        log.info("Buscando cómic con ID: {}", id);
        Comic comic = comicRepository.findById(id).orElseThrow(() -> new RuntimeException("¡El Cómic no existe en los registros!"));
        return convertirADTO(comic);
    }

    //buscar por nombre exacto
    public ComicDTO buscarPorNombreExacto(String titulo){
        log.info("Buscando cómic con título exacto: {}", titulo);
        Comic comic = comicRepository.buscarPorNombreExacto(titulo);
        if (comic == null) {
            throw new RuntimeException("¡El Cómic no existe en los registros!");
        }
        return convertirADTO(comic);
    }

}
