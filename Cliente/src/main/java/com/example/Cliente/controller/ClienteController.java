package com.example.Cliente.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Cliente.DTO.ClienteDTO;
import com.example.Cliente.model.Cliente;
import com.example.Cliente.service.ClienteService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    //Mostrar los clientes
    @GetMapping
    public ResponseEntity<List<ClienteDTO>> todosLosClientes(){
        log.info("Obteniendo todos los clientes");
        List<ClienteDTO> clientes = clienteService.obtenerTodos();
        if(clientes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    //Buscar por Id
    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> buscarPorId(@PathVariable Integer id){
        log.info("Buscando cliente con ID: {}", id);
        try {
            ClienteDTO cliente = clienteService.buscarPorId(id);
            return new ResponseEntity<>(cliente, HttpStatus.OK);
        } catch (RuntimeException e) {
            log.error("Error al buscar cliente con ID: {}: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    //Buscar por Rut
    @GetMapping("/rut/{rut}")
    public ResponseEntity<ClienteDTO> buscarPorRut(@PathVariable String rut){
        log.info("Buscando cliente con RUT: {}", rut);
        List<ClienteDTO> clientes = clienteService.buscarPorRut(rut);
        if(clientes.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }    
        return new ResponseEntity<>(clientes.get(0), HttpStatus.OK);
    }  
    
    //Guardar cliente
    @PostMapping
    public ResponseEntity<Cliente> agregarCliente(@Valid @RequestBody Cliente cliente) {
        log.info("Agregando nuevo cliente: {}", cliente);
        try {
            Cliente guardado = clienteService.guardarCliente(cliente);
            return new ResponseEntity<>(guardado, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error al agregar cliente: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    //Editar Cliente
    @PatchMapping("/{id}")
    public ResponseEntity<Cliente> editarCliente(@PathVariable Integer id, @Valid @RequestBody Cliente cliente) {
        log.info("Editando cliente con ID: {}", id);
        try {
            Cliente editado = clienteService.guardarCliente(cliente);
            return new ResponseEntity<>(editado, HttpStatus.OK);
        } catch (RuntimeException e) {
            log.error("Error al editar cliente con ID: {}: {}", id, e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Actualizar cliente
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> actualizarCliente(@PathVariable Integer id, @Valid @RequestBody Cliente cliente){
        log.info("Actualizando cliente con ID: {}", id);
        try{
            Cliente newCliente = clienteService.actualizarclientes( id, cliente);
            return new ResponseEntity<>(newCliente, HttpStatus.OK);
        }catch (RuntimeException e) {
            log.error("Error al actualizar cliente con ID: {}: {}", id, e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Eliminar cliente
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarCliente(@PathVariable Integer id) {
        log.info("Eliminando cliente con ID: {}", id);
        String resultado = clienteService.eliminarcliente(id);
        
        // Si el mensaje contiene "exitosamente", es un éxito
        if (resultado.contains("exitosamente")) {
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
        }
    }


}