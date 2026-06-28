package com.example.tiendas.controller.V1;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tiendas.DTO.DuenoDTO;
import com.example.tiendas.model.Dueno;
import com.example.tiendas.service.DuenoService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/duenos")
public class DuenoController {

    @Autowired
    private DuenoService duenoService;

    @GetMapping
    public ResponseEntity<List<DuenoDTO>> todosLosDuenos(){
        log.info("Obteniendo todos los duenos");
        List<DuenoDTO> dueno = duenoService.obtenerTodos();
        if(dueno.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(dueno, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Dueno> agregarDueno(@Valid @RequestBody Dueno dueno) {
        log.info("Agregando nuevo dueno: {}", dueno);
        try {
            Dueno guardado = duenoService.guardarDueno(dueno);
            return new ResponseEntity<>(guardado, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error al agregar dueno: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Dueno> editarDueno(@PathVariable Integer id, @Valid @RequestBody Dueno dueno) {
        log.info("Editando dueno con ID: {}", id);
        try {
            Dueno editado = duenoService.guardarDueno(dueno);
            return new ResponseEntity<>(editado, HttpStatus.OK);
        } catch (RuntimeException e) {
            log.error("Error al editar dueno con ID: {}: {}", id, e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Dueno> actualizarDueno(@PathVariable Integer id, @Valid @RequestBody Dueno dueno){
        log.info("Actualizando dueno con ID: {}", id);
        try{
            Dueno newDueno = duenoService.actualizarDueno( id, dueno);
            return new ResponseEntity<>(newDueno, HttpStatus.OK);
        }catch (RuntimeException e) {
            log.error("Error al actualizar dueno con ID: {}: {}", id, e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarDueno(@PathVariable Integer id) {
        log.info("Eliminando dueno con ID: {}", id);
        String resultado = duenoService.eliminardueno(id);
        if (resultado.contains("exitosamente")) {
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<DuenoDTO> buscarPorId(@PathVariable Integer id){
        log.info("Buscando dueno con ID: {}", id);
        try {
            DuenoDTO dueno = duenoService.buscarPorId(id);
            return new ResponseEntity<>(dueno, HttpStatus.OK);
        } catch (RuntimeException e) {
            log.error("Error al buscar dueno con ID: {}: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/rut/{rut}")
    public ResponseEntity<DuenoDTO> buscarPorRut(@PathVariable Integer rut){
        log.info("Buscando dueno con RUT: {}", rut);
        List<DuenoDTO> duenos = duenoService.buscarPorRut(rut);
        if(duenos.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }    
        return new ResponseEntity<>(duenos.get(0), HttpStatus.OK);
    } 

}
