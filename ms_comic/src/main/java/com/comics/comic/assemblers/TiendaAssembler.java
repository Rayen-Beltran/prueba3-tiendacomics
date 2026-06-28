package com.comics.comic.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.comics.comic.DTO.TiendaDTO;
import com.comics.comic.controller.V2.TiendaController2;

@Component
public class TiendaAssembler implements RepresentationModelAssembler<TiendaDTO, EntityModel<TiendaDTO>> {
    @Override
    public EntityModel<TiendaDTO> toModel(TiendaDTO tiendaDTO) {
        return EntityModel.of(tiendaDTO,
                linkTo(methodOn(TiendaController2.class).porId(tiendaDTO.getId_tienda())).withSelfRel());
    }
}
