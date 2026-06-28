package com.empleado.empleados.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.empleado.empleados.DTO.EmpleadoDTO;
import com.empleado.empleados.controller.v2.EmpleadoController;

@Component
public class EmpleadoModelAssembler implements RepresentationModelAssembler<EmpleadoDTO, EntityModel<EmpleadoDTO>> {

    @Override
    public EntityModel<EmpleadoDTO> toModel(EmpleadoDTO empleado) {
        return EntityModel.of(empleado,
                linkTo(methodOn(EmpleadoController.class).obtenerPorRut(empleado.getRut())).withSelfRel(),
                linkTo(methodOn(EmpleadoController.class).obtenerTodos()).withRel("empleados")
        );
    }
}
