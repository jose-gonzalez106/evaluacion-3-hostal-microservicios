package com.example.hostadmin.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.example.hostadmin.DTO.HostalDTO;
import com.example.hostadmin.controller.v2.HostalController;

@Component
public class HostalModelAssembler
        implements RepresentationModelAssembler<HostalDTO, EntityModel<HostalDTO>> {

    @Override
    public EntityModel<HostalDTO> toModel(HostalDTO hostal) {
        return EntityModel.of(hostal,
                linkTo(methodOn(HostalController.class).obtenerPorId(hostal.getId())).withSelfRel(),
                linkTo(methodOn(HostalController.class).obtenerTodos()).withRel("hostales"));
    }
}
