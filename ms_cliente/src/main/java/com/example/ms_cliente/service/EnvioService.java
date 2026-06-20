package com.example.ms_cliente.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ms_cliente.DTO.EnvioDTO;
import com.example.ms_cliente.model.Envio;
import com.example.ms_cliente.repository.EnvioRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class EnvioService {

    @Autowired
    private EnvioRepository envioRepository;
    
    //Mostrar todos los Envios
    public List<EnvioDTO> obtenerTodos() {
        log.info("Obteniendo todos los envios");
        return envioRepository.findAll().stream()
                 .map(this::convertirADTO)
                 .toList();
    }

    //Eliminar por Id
    public String eliminarEnvio(Integer id){
        try {
            Envio envio = envioRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("¡Imposible eliminar el envio! El Envio con ID " + id + "no existe"));
            envioRepository.delete(envio);
            log.info("Envio eliminado: {}", envio.getIdEnvio());
            return "El Envio '" + envio.getIdEnvio() + "' ha sido eliminado correctamente. ";
        } catch (RuntimeException e) {
            log.error("Error al eliminar envio con ID: {}: {}", id, e.getMessage());
            return e.getMessage();
        }
    }
    
    //Guardar Envio
    public Envio guardarEnvio(Envio envio){
        log.info("Guardando envio: {}", envio.getIdEnvio());
        return envioRepository.save(envio);
    }

    
    //Actualizar Envio
    public Envio actualizarEnvio(Integer id, Envio envio){
        log.info("Actualizando envio: {}", envio.getIdEnvio());
        Envio envio1 = envioRepository.findById(id).orElseThrow(() -> new RuntimeException("¡El Envio no existe en los registros!"));
        
        if (envio.getFechaSalida() != null) {
            envio1.setFechaSalida(envio.getFechaSalida());
        }
        if (envio.getFechaEntrega() != null) {
            envio1.setFechaEntrega(envio.getFechaEntrega());
        }
        if (envio.getTipoEnvio() != null) {
            envio1.setTipoEnvio(envio.getTipoEnvio());
        }
        if (envio.getSucursal() != null) {
            envio1.setSucursal(envio.getSucursal());
        }
        
        log.info("Envio actualizado: {}", envio1.getIdEnvio());
        return envioRepository.save(envio1);
    }

    //Buscar por id
    public EnvioDTO buscarPorId(Integer id) {
        log.info("Buscando envio por ID: {}", id);
        Envio envio = envioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("¡Envio no encontrado!"));
        return convertirADTO(envio);
    }


    //convertir a dto
    private EnvioDTO convertirADTO(Envio envio){
        EnvioDTO dto = new EnvioDTO();
        dto.setIdEnvio(envio.getIdEnvio()); 
        dto.setFechaSalida(envio.getFechaSalida());
        dto.setFechaEntrega(envio.getFechaEntrega());
        dto.setTipoEnvio(envio.getTipoEnvio());
        dto.setSucursal(envio.getSucursal());
        return dto;
    }
    
}
