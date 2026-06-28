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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tiendas.DTO.EmpleadoDTO;
import com.example.tiendas.model.Empleado;
import com.example.tiendas.service.EmpleadoService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/empleados")
public class EmpleadoController {

    @Autowired
    private EmpleadoService empleadoService;

    @GetMapping
    public ResponseEntity<List<EmpleadoDTO>> todosLosEmpleados(){
        log.info("Obteniendo todos los empleados");
        List<EmpleadoDTO> empleado = empleadoService.obtenerTodos();
        if(empleado.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(empleado, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Empleado> agregarEmpleado(@Valid @RequestBody Empleado empleado) {
        log.info("Agregando nuevo empleado: {}", empleado);
        try {
            Empleado guardado = empleadoService.guardarEmpleado(empleado);
            return new ResponseEntity<>(guardado, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error al agregar empleado: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Empleado> editarEmpleado(@PathVariable Integer id, @Valid @RequestBody Empleado empleado) {
        log.info("Editando empleado con ID: {}", id);
        try {
            Empleado editado = empleadoService.guardarEmpleado(empleado);
            return new ResponseEntity<>(editado, HttpStatus.OK);
        } catch (RuntimeException e) {
            log.error("Error al editar empleado con ID: {}: {}", id, e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Empleado> actualizarEmpleado(@PathVariable Integer id, @Valid @RequestBody Empleado empleado){
        log.info("Actualizando empleado con ID: {}", id);
        try{
            Empleado newEmpleado = empleadoService.actualizarEmpleado( id, empleado);
            return new ResponseEntity<>(newEmpleado, HttpStatus.OK);
        }catch (RuntimeException e) {
            log.error("Error al actualizar empleado con ID: {}: {}", id, e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarEmpleado(@PathVariable Integer id) {
        log.info("Eliminando empleado con ID: {}", id);
        String resultado = empleadoService.eliminarempleado(id);
        if (resultado.contains("exitosamente")) {
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpleadoDTO> buscarPorId(@PathVariable Integer id){
        log.info("Buscando empleado con ID: {}", id);
        try {
            EmpleadoDTO empleado = empleadoService.buscarPorId(id);
            return new ResponseEntity<>(empleado, HttpStatus.OK);
        } catch (RuntimeException e) {
            log.error("Error al buscar empleado con ID: {}: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/rut/{rut}")
    public ResponseEntity<EmpleadoDTO> buscarPorRut(@PathVariable Integer rut){
        log.info("Buscando empleado con RUT: {}", rut);
        List<EmpleadoDTO> empleados = empleadoService.buscarPorRut(rut);
        if(empleados.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }    
        return new ResponseEntity<>(empleados.get(0), HttpStatus.OK);
    }  



}
