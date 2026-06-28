package com.example.tiendas.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import com.example.tiendas.DTO.TiendaDTO;
import com.example.tiendas.controller.V2.EmpleadoControllerV2;
import com.example.tiendas.controller.V2.TiendaControllerV2;

@Component
public class TiendaModelAssembler implements RepresentationModelAssembler<TiendaDTO, EntityModel<TiendaDTO>>{

    @Override
    public EntityModel<TiendaDTO> toModel(TiendaDTO tienda) {
        return EntityModel.of(tienda,
                linkTo(methodOn(TiendaControllerV2.class).buscarPorId(tienda.getIdTienda())).withSelfRel(),
                linkTo(methodOn(TiendaControllerV2.class).obtenerTodas()).withRel("tiendas"));
    }

}
