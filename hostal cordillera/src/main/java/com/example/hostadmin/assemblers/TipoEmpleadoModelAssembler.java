package com.example.hostadmin.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.example.hostadmin.DTO.TipoEmpleadoDTO;
import com.example.hostadmin.controller.v2.TipoEmpleadoController;

@Component
public class TipoEmpleadoModelAssembler
        implements RepresentationModelAssembler<TipoEmpleadoDTO, EntityModel<TipoEmpleadoDTO>> {

    @Override
    public EntityModel<TipoEmpleadoDTO> toModel(TipoEmpleadoDTO tipo) {
        return EntityModel.of(tipo,
                linkTo(methodOn(TipoEmpleadoController.class).obtenerPorId(tipo.getId())).withSelfRel(),
                linkTo(methodOn(TipoEmpleadoController.class).obtenerTodos()).withRel("tipos-empleado"));
    }
}
