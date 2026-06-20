package com.example.tiendas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tiendas.DTO.DuenoDTO;
import com.example.tiendas.model.Dueno;
import com.example.tiendas.repository.DuenoRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DuenoService {

    @Autowired
    private DuenoRepository duenoRepository;

    public List<DuenoDTO> obtenerTodos() {
        log.info("Obteniendo todos los Duenos");
        return duenoRepository.findAll().stream()
                 .map(this::convertirADTO)
                 .toList();
    }

    public Dueno guardarDueno(Dueno dueno){
        log.info("Guardando Dueno: {}", dueno.getNombre());
        return duenoRepository.save(dueno);
    }

    public Dueno actualizarDueno(Integer id, Dueno dueno){
        log.info("Actualizando dueno: {}", dueno.getNombre());
        Dueno dueno1 = duenoRepository.findById(id).orElseThrow(() -> new RuntimeException("¡El Dueno no existe en los registros!"));
        
        if(dueno.getNombre() != null){
            dueno1.setNombre(dueno.getNombre());
        }
        if(dueno.getApellido() != null){
            dueno1.setApellido(dueno.getApellido());
        }
        log.info("Dueno actualizado: {}", dueno1.getNombre());
        return duenoRepository.save(dueno1);
    }

    public String eliminardueno(Integer id){
        try {
            Dueno dueno = duenoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("¡Imposible eliminar al dueno! El dueno con ID " + id + "no existe"));
            duenoRepository.delete(dueno);
            log.info("dueno eliminado: {}", dueno.getNombre());
            return "El dueno '" + dueno.getNombre() + "' ha sido eliminado correctamente. ";
        } catch (RuntimeException e) {
            log.error("Error al eliminar dueno: {}", e.getMessage());
            return e.getMessage();
        }
    }

    public List<DuenoDTO> buscarPorRut(Integer rut) {
        log.info("Buscando dueno por Rut: {}", rut);
        Dueno dueno = duenoRepository.findByRut(rut);
        if (dueno != null) {
            log.info("dueno encontrado por Rut: {}", dueno.getNombre());
            return List.of(convertirADTO(dueno));
        } else {
            log.warn("No se encontró dueno con Rut: {}", rut);
            return List.of();
        }
    }

    public DuenoDTO buscarPorId(Integer id) {
        log.info("Buscando dueno por ID: {}", id);
        Dueno dueno = duenoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("¡Dueno no encontrado!"));
            return convertirADTO(dueno);
    }

    private DuenoDTO convertirADTO(Dueno dueno){
        DuenoDTO dto = new DuenoDTO();
        dto.setIdDueno(dueno.getId());
        dto.setNombre(dueno.getNombre());
        dto.setApellido(dueno.getApellido());
        return dto;
    }

}
