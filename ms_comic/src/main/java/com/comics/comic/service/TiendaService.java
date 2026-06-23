package com.comics.comic.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import com.comics.comic.DTO.TiendaDTO;
import com.comics.comic.model.Tienda;
import com.comics.comic.repository.TiendaRepository;

@Service
@Transactional
@Slf4j
public class TiendaService {
    @Autowired
    private TiendaRepository tiendaRepository;

    public List<TiendaDTO> obtenerTodos() {
        log.info("Obteniendo todas las tiendas");
        return tiendaRepository.findAll().stream()
                 .map(this::convertirADTO)
                 .toList();
    }

    private TiendaDTO convertirADTO(Tienda tienda) {
        TiendaDTO dto = new TiendaDTO();
        dto.setId_tienda(tienda.getId_tienda());
        dto.setNombre(tienda.getNombre_tienda());
        return dto;
    }

    //Guardar tienda
    public Tienda guardarTienda(Tienda tienda){
        log.info("Guardando tienda: {}", tienda.getNombre_tienda());
        return tiendaRepository.save(tienda);
    }

    //Actualizar tienda
    public Tienda actualizarTienda(Integer id, Tienda tienda){
        log.info("Actualizando tienda: {}", tienda.getNombre_tienda());
        Tienda tienda1 = tiendaRepository.findById(id).orElseThrow(() -> new RuntimeException("¡La Tienda no existe en los registros!"));
        
        if(tienda.getNombre_tienda() != null){
            tienda1.setNombre_tienda(tienda.getNombre_tienda());
        }
        return tiendaRepository.save(tienda1);
    }

    //Eliminar tienda
    public void eliminarTienda(Integer id){
        log.info("Eliminando tienda con ID: {}", id);
        Tienda tienda = tiendaRepository.findById(id).orElseThrow(() -> new RuntimeException("¡La Tienda no existe en los registros!"));
        tiendaRepository.delete(tienda);
    }

    //buscar por id
    public TiendaDTO findById(Integer id){
        log.info("Buscando tienda con ID: {}", id);
        Tienda tienda = tiendaRepository.findById(id).orElseThrow(() -> new RuntimeException("¡La Tienda no existe en los registros!"));
        return convertirADTO(tienda);
    }
}