package com.huesped.huespedes.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.huesped.huespedes.controller.v2.HuespedController;
import com.huesped.huespedes.DTO.HuespedDTO;

@Component
public class HuespedModelAssembler implements RepresentationModelAssembler<HuespedDTO, EntityModel<HuespedDTO>> {

    @Override
    public EntityModel<HuespedDTO> toModel(HuespedDTO huesped) {
        return EntityModel.of(huesped,
            linkTo(methodOn(HuespedController.class).obtenerPorRun(huesped.getRun())).withSelfRel(),
            linkTo(methodOn(HuespedController.class).obtenerTodos()).withRel("huespedes")
        );
    }
}