package com.example.ms_cliente.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.example.ms_cliente.DTO.EnvioDTO;
import com.example.ms_cliente.controller.EnvioController;

@Component
public class EnvioModelAssembler implements RepresentationModelAssembler<EnvioDTO, EntityModel<EnvioDTO>> {

    @Override
    public EntityModel<EnvioDTO> toModel(EnvioDTO envio) {
        return EntityModel.of(envio,
            linkTo(methodOn(EnvioController.class).buscarPorId(envio.getIdEnvio())).withSelfRel(),
            linkTo(methodOn(EnvioController.class).todosLosEnvios()).withRel("envios")
        );
    }
}