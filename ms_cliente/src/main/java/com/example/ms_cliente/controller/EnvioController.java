package com.example.ms_cliente.controller;


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

import com.example.ms_cliente.DTO.EnvioDTO;
import com.example.ms_cliente.model.Envio;
import com.example.ms_cliente.service.EnvioService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/envios")
public class EnvioController {

    @Autowired
    private EnvioService envioService;

    //Mostrar Los Envios
    @GetMapping
    public ResponseEntity<List<EnvioDTO>> todosLosEnvios(){
        log.info("Obteniendo todos los envios");
        List<EnvioDTO> envios = envioService.obtenerTodos();
        if(envios.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(envios, HttpStatus.OK);
    }

    //Buscar por Id
    @GetMapping("/{id}")
    public ResponseEntity<EnvioDTO> buscarPorId(@PathVariable Integer id){
        log.info("Buscando envio por ID: {}", id);
        try {
            EnvioDTO envio = envioService.buscarPorId(id);
            return new ResponseEntity<>(envio, HttpStatus.OK);
        } catch (RuntimeException e) {
            log.error("Error al buscar envio con ID: {}: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    
    //Guardar Envio
    @PostMapping
    public ResponseEntity<Envio> agregarEnvio(@Valid @RequestBody Envio envio) {
        log.info("Guardando envio: {}", envio.getIdEnvio());
        try {
            Envio guardado = envioService.guardarEnvio(envio);
            return new ResponseEntity<>(guardado, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error al guardar envio: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    //Editar Envio
    @PatchMapping("/{id}")
    public ResponseEntity<Envio> editarEnvio(@PathVariable Integer id, @Valid @RequestBody Envio envio) {
        log.info("Editando envio con ID: {}", id);
        try {
            Envio editado = envioService.guardarEnvio(envio);
            return new ResponseEntity<>(editado, HttpStatus.OK);
        } catch (RuntimeException e) {
            log.error("Error al editar envio con ID: {}: {}", id, e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Actualizar Envio
    @PutMapping("/{id}")
    public ResponseEntity<Envio> actualizarEnvio(@PathVariable Integer id, @Valid @RequestBody Envio envio){
        log.info("Actualizando envio con ID: {}", id);
        try{
            Envio newEnvio = envioService.actualizarEnvio(id, envio);
            return new ResponseEntity<>(newEnvio, HttpStatus.OK);
        }catch (RuntimeException e) {
            log.error("Error al actualizar envio con ID: {}: {}", id, e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Eliminar Envio
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarEnvio(@PathVariable Integer id) {
        log.info("Eliminando envio con ID: {}", id);
        String resultado = envioService.eliminarEnvio(id);
        
        // Si el mensaje contiene "exitosamente", es un éxito
        if (resultado.contains("exitosamente")) {
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
        }
    }


}