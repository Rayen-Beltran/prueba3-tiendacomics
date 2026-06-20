package com.example.tiendas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.tiendas.DTO.TiendaDTO;
import com.example.tiendas.model.Tienda;
import com.example.tiendas.repository.TiendaRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TiendaService {

    @Autowired
    private TiendaRepository tiendaRepository;

    public List<TiendaDTO> obtenerTodas() {
        log.info("Obteniendo todas las tiendas");
        return tiendaRepository.findAll().stream()
                 .map(this::convertirADTO)
                 .toList();
    }

    public Tienda guardarTienda(Tienda tienda){
        log.info("Guardando tienda: {}", tienda.getNombre());
        return tiendaRepository.save(tienda);
    }

    public Tienda actualizarTienda(Integer id, Tienda tienda){
        log.info("Actualizando tienda: {}", tienda.getNombre());
        Tienda tienda1 = tiendaRepository.findById(id).orElseThrow(() -> new RuntimeException("¡La Tienda no existe en los registros!"));
        
        if(tienda.getNombre() != null){
            tienda1.setNombre(tienda.getNombre());
        }
        if(tienda.getDireccion() != null){
            tienda1.setDireccion(tienda.getDireccion());
        }
        log.info("Tienda actualizada: {}", tienda1.getNombre());
        return tiendaRepository.save(tienda1);
    }

    public String eliminarTienda(Integer id){
        try {
            Tienda tienda = tiendaRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("¡Imposible eliminar la Tienda! La Tienda con ID " + id + "no existe"));
            tiendaRepository.delete(tienda);
            log.info("Tienda eliminada: {}", tienda.getNombre());
            return "La Tienda '" + tienda.getNombre() + "' ha sido eliminada correctamente. ";
        } catch (RuntimeException e) {
            log.error("Error al eliminar la Tienda: {}", e.getMessage());
            return e.getMessage();
        }
    }

    public TiendaDTO buscarPorId(Integer id) {
        log.info("Buscando Tienda por ID: {}", id);
        Tienda tienda = tiendaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("¡Tienda no encontrado!"));
            return convertirADTO(tienda);
    }

     private TiendaDTO convertirADTO(Tienda tienda){
        TiendaDTO dto = new TiendaDTO();
        dto.setIdTienda(tienda.getId());
        dto.setNombre(tienda.getNombre());
        dto.setDireccion(tienda.getDireccion());
        return dto;
    }

}
