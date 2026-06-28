package com.comics.comic.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.comics.comic.DTO.ComicDTO;
import com.comics.comic.controller.V2.ComicController2;

@Component
public class ComicAssembler implements RepresentationModelAssembler<ComicDTO, EntityModel<ComicDTO>> {

    @Override
    public EntityModel<ComicDTO> toModel(ComicDTO comicDTO) {
        return EntityModel.of(comicDTO,
                linkTo(methodOn(ComicController2.class).porId(comicDTO.getId_comic())).withSelfRel());
    }
}
