package com.example.ms_cliente.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.example.ms_cliente.DTO.PagoDTO;
import com.example.ms_cliente.controller.PagoController;

@Component
public class PagoModelAssembler implements RepresentationModelAssembler<PagoDTO, EntityModel<PagoDTO>> {

    @Override
    public EntityModel<PagoDTO> toModel(PagoDTO pago) {
        return EntityModel.of(pago,
            linkTo(methodOn(PagoController.class).obtenerPagoPorId(pago.getId_pago())).withSelfRel()
        );
    }
}