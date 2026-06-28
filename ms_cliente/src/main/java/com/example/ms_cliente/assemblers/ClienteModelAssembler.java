package com.example.ms_cliente.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.example.ms_cliente.DTO.ClienteDTO;
import com.example.ms_cliente.controller.ClienteController;

@Component
public class ClienteModelAssembler implements RepresentationModelAssembler<ClienteDTO, EntityModel<ClienteDTO>> {

    @Override
    public EntityModel<ClienteDTO> toModel(ClienteDTO cliente) {
        return EntityModel.of(cliente,
            linkTo(methodOn(ClienteController.class).buscarPorId(cliente.getId_cliente())).withSelfRel(),
            linkTo(methodOn(ClienteController.class).todosLosClientes()).withRel("clientes")
        );
    }
}
