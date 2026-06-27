package com.example.tiendas.controller;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tiendas.service.TiendaService;
import com.github.andrewoma.dexx.collection.List;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

import com.example.tiendas.DTO.TiendaDTO;
import com.example.tiendas.assemblers.TiendaModelAssembler;
import com.example.tiendas.model.Tienda;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v2/tiendas")
public class TiendaControllerV2 {
 
    @Autowired
    private TiendaService tiendaService;
 
    @Autowired
    private TiendaModelAssembler assembler;
 
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<TiendaDTO>> obtenerTodas(){
        log.info("Obteniendo todas las tiendas");
        java.util.List<EntityModel<TiendaDTO>> tiendas = tiendaService.obtenerTodas().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(tiendas,
                linkTo(methodOn(TiendaControllerV2.class).obtenerTodas()).withSelfRel());
    }
 
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Tienda>> guardarTienda(@Valid @RequestBody Tienda tienda) {
        log.info("Guardando tienda: {}", tienda.getNombre());
        Tienda guardada = tiendaService.guardarTienda(tienda);
        return ResponseEntity
                .created(linkTo(methodOn(TiendaControllerV2.class).buscarPorId(guardada.getId())).toUri())
                .body(EntityModel.of(guardada,
                        linkTo(methodOn(TiendaControllerV2.class).buscarPorId(guardada.getId())).withSelfRel(),
                        linkTo(methodOn(TiendaControllerV2.class).obtenerTodas()).withRel("tiendas")));
    }
 
    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Tienda>> actualizarTienda(@PathVariable Integer id, @Valid @RequestBody Tienda tienda){
        log.info("Actualizando tienda con ID: {}", id);
        Tienda newTienda = tiendaService.actualizarTienda(id, tienda);
        return ResponseEntity.ok(EntityModel.of(newTienda,
                linkTo(methodOn(TiendaControllerV2.class).buscarPorId(id)).withSelfRel(),
                linkTo(methodOn(TiendaControllerV2.class).obtenerTodas()).withRel("tiendas")));
    }
 
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<?> eliminarTienda(@PathVariable Integer id) {
        log.info("Eliminando tienda con ID: {}", id);
        tiendaService.eliminarTienda(id);
        return ResponseEntity.noContent().build();
    }
 
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<TiendaDTO>> buscarPorId(@PathVariable Integer id){
        log.info("Buscando tienda con ID: {}", id);
        TiendaDTO tienda = tiendaService.buscarPorId(id);
        return ResponseEntity.ok(assembler.toModel(tienda));
    }
 
}
