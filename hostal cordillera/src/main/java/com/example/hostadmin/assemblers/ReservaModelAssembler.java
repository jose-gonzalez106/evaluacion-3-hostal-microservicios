package com.example.hostadmin.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.example.hostadmin.DTO.ReservaDTO;
import com.example.hostadmin.controller.v2.ReservaController;

@Component
public class ReservaModelAssembler
        implements RepresentationModelAssembler<ReservaDTO, EntityModel<ReservaDTO>> {

    @Override
    public EntityModel<ReservaDTO> toModel(ReservaDTO reserva) {
        return EntityModel.of(reserva,
                linkTo(methodOn(ReservaController.class).obtenerPorId(reserva.getId())).withSelfRel(),
                linkTo(methodOn(ReservaController.class).obtenerTodas()).withRel("reservas"));
    }
}
