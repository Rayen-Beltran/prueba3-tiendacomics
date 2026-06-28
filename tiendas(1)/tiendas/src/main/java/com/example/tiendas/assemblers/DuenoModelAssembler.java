package com.example.tiendas.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.example.tiendas.DTO.DuenoDTO;
import com.example.tiendas.controller.V2.DuenoControllerV2;

@Component
public class DuenoModelAssembler implements RepresentationModelAssembler<DuenoDTO, EntityModel<DuenoDTO>> {
 
    @Override
    public EntityModel<DuenoDTO> toModel(DuenoDTO dueno) {
        return EntityModel.of(dueno,
                linkTo(methodOn(DuenoControllerV2.class).buscarPorId(dueno.getIdDueno())).withSelfRel(),
                linkTo(methodOn(DuenoControllerV2.class).todosLosDuenos()).withRel("duenos"));
    }
}