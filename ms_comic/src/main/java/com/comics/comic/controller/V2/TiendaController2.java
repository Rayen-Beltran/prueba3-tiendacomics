package com.comics.comic.controller.V2;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import com.comics.comic.DTO.TiendaDTO;
import com.comics.comic.assemblers.TiendaAssembler;
import com.comics.comic.model.Tienda;
import com.comics.comic.service.TiendaService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController("TiendaControllerV2")
@RequestMapping("/api/v2/tiendas")
@Slf4j
public class TiendaController2 {

    @Autowired
    private TiendaService tiendaService;

    @Autowired
    private TiendaAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<TiendaDTO>>> todos() {
        List<EntityModel<TiendaDTO>> tiendas = tiendaService.obtenerTodos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        if (tiendas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CollectionModel.of(
                tiendas,
                linkTo(methodOn(TiendaController2.class).todos()).withSelfRel()));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<TiendaDTO>> porId(@PathVariable Integer id) {
        try {
            TiendaDTO dto = tiendaService.findById(id);
            if (dto == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(assembler.toModel(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<TiendaDTO>> registrar(@Valid @RequestBody Tienda tienda) {
        try {
            Tienda savedTienda = tiendaService.guardarTienda(tienda);
            TiendaDTO dto = tiendaService.findById(savedTienda.getId_tienda());
            return ResponseEntity
                    .created(linkTo(methodOn(TiendaController2.class).porId(dto.getId_tienda())).toUri())
                    .body(assembler.toModel(dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
