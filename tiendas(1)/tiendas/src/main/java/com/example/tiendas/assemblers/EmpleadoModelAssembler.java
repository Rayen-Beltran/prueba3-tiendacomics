package com.example.tiendas.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import com.example.tiendas.DTO.EmpleadoDTO;
import com.example.tiendas.controller.EmpleadoControllerV2;

public class EmpleadoModelAssembler implements RepresentationModelAssembler<EmpleadoDTO, EntityModel<EmpleadoDTO>> {

    @Override
    public EntityModel<EmpleadoDTO> toModel(EmpleadoDTO empleado) {
        return EntityModel.of(empleado,
                linkTo(methodOn(EmpleadoControllerV2.class).buscarPorId(empleado.getIdEmpleado())).withSelfRel(),
                linkTo(methodOn(EmpleadoControllerV2.class).todosLosEmpleados()).withRel("empleados"));
    }

}
