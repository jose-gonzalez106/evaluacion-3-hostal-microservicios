package com.example.hostadmin.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.example.hostadmin.DTO.HabitacionDTO;
import com.example.hostadmin.controller.v2.HabitacionController;

@Component
public class HabitacionModelAssembler
        implements RepresentationModelAssembler<HabitacionDTO, EntityModel<HabitacionDTO>> {

    @Override
    public EntityModel<HabitacionDTO> toModel(HabitacionDTO habitacion) {
        return EntityModel.of(habitacion,
                linkTo(methodOn(HabitacionController.class).obtenerPorNumero(habitacion.getNumero())).withSelfRel(),
                linkTo(methodOn(HabitacionController.class).obtenerTodas()).withRel("habitaciones"));
    }
}
