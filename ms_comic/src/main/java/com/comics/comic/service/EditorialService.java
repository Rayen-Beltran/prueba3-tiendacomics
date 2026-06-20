package com.comics.comic.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import com.comics.comic.DTO.EditorialDTO;
import com.comics.comic.model.Editorial;
import com.comics.comic.repository.EditorialRepository;

@Service
@Transactional
@Slf4j
public class EditorialService {
    @Autowired
    private EditorialRepository editorialRepository;

    public List<EditorialDTO> obtenerTodos() {
        log.info("Obteniendo todas las editoriales");
        return editorialRepository.findAll().stream()
                 .map(this::convertirADTO)
                 .toList();
    }

    private EditorialDTO convertirADTO(Editorial editorial) {
        EditorialDTO dto = new EditorialDTO();
        dto.setId_editorial(editorial.getId_editorial());
        dto.setNombre(editorial.getNombre());
        return dto;
    }

    //Guardar editorial
    public Editorial guardarEditorial(Editorial editorial){
        log.info("Guardando editorial: {}", editorial.getNombre());
        return editorialRepository.save(editorial);
    }

    //Actualizar editorial
    public Editorial actualizarEditorial(Integer id, Editorial editorial){
        log.info("Actualizando editorial: {}", editorial.getNombre());
        Editorial editorial1 = editorialRepository.findById(id).orElseThrow(() -> new RuntimeException("¡La Editorial no existe en los registros!"));
        
        if(editorial.getNombre() != null){
            editorial1.setNombre(editorial.getNombre());
        }
        return editorialRepository.save(editorial1);
    }

    //Eliminar editorial
    public void eliminarEditorial(Integer id){
        log.info("Eliminando editorial con ID: {}", id);
        Editorial editorial = editorialRepository.findById(id).orElseThrow(() -> new RuntimeException("¡La Editorial no existe en los registros!"));
        editorialRepository.delete(editorial);
    }

    //buscar por id
    public EditorialDTO buscarPorId(Integer id){
        log.info("Buscando editorial con ID: {}", id);
        Editorial editorial = editorialRepository.findById(id).orElseThrow(() -> new RuntimeException("¡La Editorial no existe en los registros!"));
        return convertirADTO(editorial);
    }

}
