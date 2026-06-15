package com.example.ms_cliente.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ms_cliente.DTO.ClienteDTO;
import com.example.ms_cliente.model.Cliente;
import com.example.ms_cliente.repository.ClienteRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;
    
    public String eliminarcliente(Integer id){
        try {
            Cliente cliente = clienteRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("¡Imposible eliminar al Cliente! El Cliente con ID " + id + "no existe"));
            clienteRepository.delete(cliente);
            log.info("Cliente eliminado: {}", cliente.getNombre());
            return "El Cliente '" + cliente.getNombre() + "' ha sido eliminado correctamente. ";
        } catch (RuntimeException e) {
            log.error("Error al eliminar cliente: {}", e.getMessage());
            return e.getMessage();
        }
    }
    
    public List<ClienteDTO> obtenerTodos() {
        log.info("Obteniendo todos los clientes");
        return clienteRepository.findAll().stream()
                 .map(this::convertirADTO)
                 .toList();
    }



    //Guardar cliente
    public Cliente guardarCliente(Cliente cliente){
        log.info("Guardando cliente: {}", cliente.getNombre());
        return clienteRepository.save(cliente);
    }


    //Actualizar cliente
    public Cliente actualizarclientes(Integer id, Cliente cliente){
        log.info("Actualizando cliente: {}", cliente.getNombre());
        Cliente cliente1 = clienteRepository.findById(id).orElseThrow(() -> new RuntimeException("¡El Cliente no existe en los registros!"));
        
        if(cliente.getNombre() != null){
            cliente1.setNombre(cliente.getNombre());
        }
        if(cliente.getApellido() != null){
            cliente1.setApellido(cliente.getApellido());
        }
        if(cliente.getEdad() != null){
            cliente1.setEdad(cliente.getEdad());
        }
        if(cliente.getCorreo() != null){
            cliente1.setCorreo(cliente.getCorreo());
        }
        if(cliente.getTelefono() != null){
            cliente1.setTelefono(cliente.getTelefono());
        }
        if(cliente.getDireccion() != null){
            cliente1.setDireccion(cliente.getDireccion());
        }
        log.info("Cliente actualizado: {}", cliente1.getNombre());
        return clienteRepository.save(cliente1);
    }

    //Buscar por Rut
    public List<ClienteDTO> buscarPorRut(String rut) {
        log.info("Buscando cliente por Rut: {}", rut);
        Cliente cliente = clienteRepository.findByRut(rut);
        if (cliente != null) {
            log.info("Cliente encontrado por Rut: {}", cliente.getNombre());
            return List.of(convertirADTO(cliente));
        } else {
            log.warn("No se encontró cliente con Rut: {}", rut);
            return List.of();
        }
    }

    public ClienteDTO buscarPorId(Integer id) {
        log.info("Buscando cliente por ID: {}", id);
        Cliente cliente = clienteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("¡Cliente no encontrado!"));
            return convertirADTO(cliente);
    }

    private ClienteDTO convertirADTO(Cliente cliente){
        ClienteDTO dto = new ClienteDTO();
        dto.setId_cliente(cliente.getId());
        dto.setNombre(cliente.getNombre());
        dto.setApellido(cliente.getApellido());
        dto.setRut(cliente.getRut()); 
        dto.setCorreo(cliente.getCorreo());
        dto.setTelefono(cliente.getTelefono());
        dto.setDireccion(cliente.getDireccion());
        dto.setDv(cliente.getDv());
        return dto;
    }
}