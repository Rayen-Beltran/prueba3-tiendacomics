package com.comics.comic.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.comics.comic.DTO.CategoriaDTO;
import com.comics.comic.controller.V2.CategoriaController2;

@Component
public class CategoriaAssembler implements RepresentationModelAssembler<CategoriaDTO, EntityModel<CategoriaDTO>> {
    @Override
    public EntityModel<CategoriaDTO> toModel(CategoriaDTO categoriaDTO) {
        return EntityModel.of(categoriaDTO,
                linkTo(methodOn(CategoriaController2.class).porId(categoriaDTO.getId_categoria())).withSelfRel());
    }
}
