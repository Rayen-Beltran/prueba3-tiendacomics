package com.comics.comic.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.comics.comic.DTO.AutorDTO;
import com.comics.comic.controller.V2.AutorController2;

@Component
public class AutorAssembler implements RepresentationModelAssembler<AutorDTO, EntityModel<AutorDTO>> {
    @Override
    public EntityModel<AutorDTO> toModel(AutorDTO autorDTO) {
        return EntityModel.of(autorDTO,
                linkTo(methodOn(AutorController2.class).porId(autorDTO.getId_autor())).withSelfRel());
    }
}
