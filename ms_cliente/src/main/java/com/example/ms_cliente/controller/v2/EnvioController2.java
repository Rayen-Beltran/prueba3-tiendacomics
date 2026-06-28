package com.example.ms_cliente.controller.v2;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.ms_cliente.DTO.EnvioDTO;
import com.example.ms_cliente.assemblers.EnvioModelAssembler;
import com.example.ms_cliente.model.Envio;
import com.example.ms_cliente.service.EnvioService;

import jakarta.validation.Valid;

@RestController("envioControllerV2")
@RequestMapping("/api/v2/envios")
public class EnvioController2 {

    @Autowired private EnvioService envioService;
    @Autowired private EnvioModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<EnvioDTO>>> todos() {
        List<EntityModel<EnvioDTO>> envios = envioService.obtenerTodos()
            .stream().map(assembler::toModel).collect(Collectors.toList());
        if (envios.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(CollectionModel.of(envios,
            linkTo(methodOn(EnvioController2.class).todos()).withSelfRel()));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<EnvioDTO>> porId(@PathVariable Integer id) {
        try {
            EnvioDTO dto = envioService.buscarPorId(id);
            if (dto == null) return ResponseEntity.notFound().build();
            return ResponseEntity.ok(assembler.toModel(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<EnvioDTO>> registrar(@Valid @RequestBody Envio envio) {
        try {
            Envio guardado = envioService.guardarEnvio(envio);
            EnvioDTO dto = envioService.buscarPorId(guardado.getIdEnvio());
            return ResponseEntity
                .created(linkTo(methodOn(EnvioController2.class).porId(dto.getIdEnvio())).toUri())
                .body(assembler.toModel(dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}