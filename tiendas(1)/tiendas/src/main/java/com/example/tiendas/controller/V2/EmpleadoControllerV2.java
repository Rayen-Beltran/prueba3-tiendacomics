package com.example.tiendas.controller.V2;

import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import com.example.tiendas.DTO.EmpleadoDTO;
import com.example.tiendas.assemblers.EmpleadoModelAssembler;
import com.example.tiendas.model.Empleado;
import com.example.tiendas.service.EmpleadoService;


import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v2/empleados")
public class EmpleadoControllerV2 {
 
    @Autowired
    private EmpleadoService empleadoService;
 
    @Autowired
    private EmpleadoModelAssembler assembler;
 
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<EmpleadoDTO>> todosLosEmpleados(){
        log.info("Obteniendo todos los empleados");
        java.util.List<EntityModel<EmpleadoDTO>> empleados = empleadoService.obtenerTodos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(empleados,
                linkTo(methodOn(EmpleadoControllerV2.class).todosLosEmpleados()).withSelfRel());
    }
 
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Empleado>> agregarEmpleado(@Valid @RequestBody Empleado empleado) {
        log.info("Agregando nuevo empleado: {}", empleado);
        Empleado guardado = empleadoService.guardarEmpleado(empleado);
        return ResponseEntity
                .created(linkTo(methodOn(EmpleadoControllerV2.class).buscarPorId(guardado.getId())).toUri())
                .body(EntityModel.of(guardado,
                        linkTo(methodOn(EmpleadoControllerV2.class).buscarPorId(guardado.getId())).withSelfRel(),
                        linkTo(methodOn(EmpleadoControllerV2.class).todosLosEmpleados()).withRel("empleados")));
    }
 
    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Empleado>> editarEmpleado(@PathVariable Integer id, @Valid @RequestBody Empleado empleado) {
        log.info("Editando empleado con ID: {}", id);
        Empleado editado = empleadoService.guardarEmpleado(empleado);
        return ResponseEntity.ok(EntityModel.of(editado,
                linkTo(methodOn(EmpleadoControllerV2.class).buscarPorId(id)).withSelfRel(),
                linkTo(methodOn(EmpleadoControllerV2.class).todosLosEmpleados()).withRel("empleados")));
    }
 
    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Empleado>> actualizarEmpleado(@PathVariable Integer id, @Valid @RequestBody Empleado empleado){
        log.info("Actualizando empleado con ID: {}", id);
        Empleado newEmpleado = empleadoService.actualizarEmpleado(id, empleado);
        return ResponseEntity.ok(EntityModel.of(newEmpleado,
                linkTo(methodOn(EmpleadoControllerV2.class).buscarPorId(id)).withSelfRel(),
                linkTo(methodOn(EmpleadoControllerV2.class).todosLosEmpleados()).withRel("empleados")));
    }
 
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<?> eliminarEmpleado(@PathVariable Integer id) {
        log.info("Eliminando empleado con ID: {}", id);
        empleadoService.eliminarempleado(id);
        return ResponseEntity.noContent().build();
    }
 
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<EmpleadoDTO>> buscarPorId(@PathVariable Integer id){
        log.info("Buscando empleado con ID: {}", id);
        EmpleadoDTO empleado = empleadoService.buscarPorId(id);
        return ResponseEntity.ok(assembler.toModel(empleado));
    }
 
    @GetMapping(value = "/rut/{rut}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<EmpleadoDTO>> buscarPorRut(@PathVariable Integer rut){
        log.info("Buscando empleado con RUT: {}", rut);
        java.util.List<EmpleadoDTO> empleados = empleadoService.buscarPorRut(rut);
        if(empleados.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(assembler.toModel(empleados.get(0)));
    }
 
}