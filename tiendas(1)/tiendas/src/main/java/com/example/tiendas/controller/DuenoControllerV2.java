package com.example.tiendas.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.tiendas.DTO.DuenoDTO;
import com.example.tiendas.model.Dueno;
import com.example.tiendas.service.DuenoService; 
import com.example.tiendas.assemblers.DuenoModelAssembler;

import java.util.List;
import java.util.stream.Collectors;
 
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
 
@Slf4j
@RestController
@RequestMapping("/api/v2/duenos")
public class DuenoControllerV2 {
 
    @Autowired
    private DuenoService duenoService;
 
    @Autowired
    private DuenoModelAssembler assembler;
 
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<DuenoDTO>>> todosLosDuenos(){
        log.info("Obteniendo todos los duenos");
        List<DuenoDTO> dueno = duenoService.obtenerTodos();
        if(dueno.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<EntityModel<DuenoDTO>> duenos = dueno.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        CollectionModel<EntityModel<DuenoDTO>> collectionModel = CollectionModel.of(duenos,
                linkTo(methodOn(DuenoControllerV2.class).todosLosDuenos()).withSelfRel());
        return new ResponseEntity<>(collectionModel, HttpStatus.OK);
    }
 
    @PostMapping
    public ResponseEntity<EntityModel<Dueno>> agregarDueno(@Valid @RequestBody Dueno dueno) {
        log.info("Agregando nuevo dueno: {}", dueno);
        try {
            Dueno guardado = duenoService.guardarDueno(dueno);
            EntityModel<Dueno> model = EntityModel.of(guardado,
                    linkTo(methodOn(DuenoControllerV2.class).buscarPorId(guardado.getId())).withSelfRel(),
                    linkTo(methodOn(DuenoControllerV2.class).todosLosDuenos()).withRel("duenos"));
            return new ResponseEntity<>(model, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error al agregar dueno: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
 
    @PatchMapping("/{id}")
    public ResponseEntity<EntityModel<Dueno>> editarDueno(@PathVariable Integer id, @Valid @RequestBody Dueno dueno) {
        log.info("Editando dueno con ID: {}", id);
        try {
            Dueno editado = duenoService.guardarDueno(dueno);
            EntityModel<Dueno> model = EntityModel.of(editado,
                    linkTo(methodOn(DuenoControllerV2.class).buscarPorId(id)).withSelfRel(),
                    linkTo(methodOn(DuenoControllerV2.class).todosLosDuenos()).withRel("duenos"));
            return new ResponseEntity<>(model, HttpStatus.OK);
        } catch (RuntimeException e) {
            log.error("Error al editar dueno con ID: {}: {}", id, e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
 
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Dueno>> actualizarDueno(@PathVariable Integer id, @Valid @RequestBody Dueno dueno){
        log.info("Actualizando dueno con ID: {}", id);
        try{
            Dueno newDueno = duenoService.actualizarDueno(id, dueno);
            EntityModel<Dueno> model = EntityModel.of(newDueno,
                    linkTo(methodOn(DuenoControllerV2.class).buscarPorId(id)).withSelfRel(),
                    linkTo(methodOn(DuenoControllerV2.class).todosLosDuenos()).withRel("duenos"));
            return new ResponseEntity<>(model, HttpStatus.OK);
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
    public ResponseEntity<EntityModel<DuenoDTO>> buscarPorId(@PathVariable Integer id){
        log.info("Buscando dueno con ID: {}", id);
        try {
            DuenoDTO dueno = duenoService.buscarPorId(id);
            EntityModel<DuenoDTO> model = assembler.toModel(dueno);
            return new ResponseEntity<>(model, HttpStatus.OK);
        } catch (RuntimeException e) {
            log.error("Error al buscar dueno con ID: {}: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
 
    @GetMapping("/rut/{rut}")
    public ResponseEntity<EntityModel<DuenoDTO>> buscarPorRut(@PathVariable Integer rut){
        log.info("Buscando dueno con RUT: {}", rut);
        List<DuenoDTO> duenos = duenoService.buscarPorRut(rut);
        if(duenos.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        EntityModel<DuenoDTO> model = assembler.toModel(duenos.get(0));
        return new ResponseEntity<>(model, HttpStatus.OK);
    }


}
