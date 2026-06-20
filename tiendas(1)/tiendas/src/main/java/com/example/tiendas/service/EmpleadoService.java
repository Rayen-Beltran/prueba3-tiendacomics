package com.example.tiendas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tiendas.DTO.EmpleadoDTO;
import com.example.tiendas.model.Empleado;
import com.example.tiendas.repository.EmpleadoRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;



    public List<EmpleadoDTO> obtenerTodos() {
        log.info("Obteniendo todos los Empleados");
        return empleadoRepository.findAll().stream()
                 .map(this::convertirADTO)
                 .toList();
    }

    public Empleado guardarEmpleado(Empleado empleado){
        log.info("Guardando Empleado: {}", empleado.getNombre());
        return empleadoRepository.save(empleado);
    }
    
    public Empleado actualizarEmpleado(Integer id, Empleado empleado){
        log.info("Actualizando empleado: {}", empleado.getNombre());
        Empleado empleado1 = empleadoRepository.findById(id).orElseThrow(() -> new RuntimeException("¡El Empleado no existe en los registros!"));
        
        if(empleado.getNombre() != null){
            empleado1.setNombre(empleado.getNombre());
        }
        if(empleado.getApellido() != null){
            empleado1.setApellido(empleado.getApellido());
        }
        if(empleado.getEdad() != null){
            empleado1.setEdad(empleado.getEdad());
        }
        if(empleado.getCorreo() != null){
            empleado1.setCorreo(empleado.getCorreo());
        }
        if(empleado.getTelefono() != null){
            empleado1.setTelefono(empleado.getTelefono());
        }
        log.info("Empleado actualizado: {}", empleado1.getNombre());
        return empleadoRepository.save(empleado1);
    }

    public String eliminarempleado(Integer id){
        try {
            Empleado empleado = empleadoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("¡Imposible eliminar al Empleado! El Empleado con ID " + id + "no existe"));
            empleadoRepository.delete(empleado);
            log.info("Empleado eliminado: {}", empleado.getNombre());
            return "El Empleado '" + empleado.getNombre() + "' ha sido eliminado correctamente. ";
        } catch (RuntimeException e) {
            log.error("Error al eliminar empleado: {}", e.getMessage());
            return e.getMessage();
        }
    }


    public List<EmpleadoDTO> buscarPorRut(Integer rut) {
        log.info("Buscando empleado por Rut: {}", rut);
        Empleado empleado = empleadoRepository.findByRut(rut);
        if (empleado != null) {
            log.info("Empleado encontrado por Rut: {}", empleado.getNombre());
            return List.of(convertirADTO(empleado));
        } else {
            log.warn("No se encontró empleado con Rut: {}", rut);
            return List.of();
        }
    }

    public EmpleadoDTO buscarPorId(Integer id) {
        log.info("Buscando Empleado por ID: {}", id);
        Empleado empleado = empleadoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("¡Empleado no encontrado!"));
            return convertirADTO(empleado);
    }

    private EmpleadoDTO convertirADTO(Empleado empleado){
        EmpleadoDTO dto = new EmpleadoDTO();
        dto.setIdEmpleado(empleado.getId());
        dto.setNombre(empleado.getNombre());
        dto.setApellido(empleado.getApellido());
        return dto;
    }

}
