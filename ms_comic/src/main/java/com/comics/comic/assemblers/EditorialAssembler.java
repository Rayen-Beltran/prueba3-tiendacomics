package com.comics.comic.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.comics.comic.DTO.EditorialDTO;
import com.comics.comic.controller.V2.EditorialController2;

@Component
public class EditorialAssembler implements RepresentationModelAssembler<EditorialDTO, EntityModel<EditorialDTO>> {
    @Override
    public EntityModel<EditorialDTO> toModel(EditorialDTO editorialDTO) {
        return EntityModel.of(editorialDTO,
                linkTo(methodOn(EditorialController2.class).porId(editorialDTO.getId_editorial())).withSelfRel());
    }
}
