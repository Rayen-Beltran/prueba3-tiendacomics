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

import com.example.ms_cliente.DTO.ClienteDTO;
import com.example.ms_cliente.assemblers.ClienteModelAssembler;
import com.example.ms_cliente.model.Cliente;
import com.example.ms_cliente.service.ClienteService;

import jakarta.validation.Valid;

@RestController("clienteControllerV2")
@RequestMapping("/api/v2/clientes")
public class ClienteController2 {

    @Autowired private ClienteService clienteService;
    @Autowired private ClienteModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<ClienteDTO>>> todos() {
        List<EntityModel<ClienteDTO>> clientes = clienteService.obtenerTodos()
            .stream().map(assembler::toModel).collect(Collectors.toList());
        if (clientes.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(CollectionModel.of(clientes,
            linkTo(methodOn(ClienteController2.class).todos()).withSelfRel()));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<ClienteDTO>> porId(@PathVariable Integer id) {
        try {
            ClienteDTO dto = clienteService.buscarPorId(id);
            if (dto == null) return ResponseEntity.notFound().build();
            return ResponseEntity.ok(assembler.toModel(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<ClienteDTO>> registrar(@Valid @RequestBody Cliente cliente) {
        try {
            Cliente guardado = clienteService.guardarCliente(cliente);
            ClienteDTO dto = clienteService.buscarPorId(guardado.getId());
            return ResponseEntity
                .created(linkTo(methodOn(ClienteController2.class).porId(dto.getId_cliente())).toUri())
                .body(assembler.toModel(dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}