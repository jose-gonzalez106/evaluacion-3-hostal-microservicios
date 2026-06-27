package com.example.hostadmin.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.example.hostadmin.DTO.ResenniaDTO;
import com.example.hostadmin.controller.v2.ResenaController;

@Component
public class ResenaModelAssembler
        implements RepresentationModelAssembler<ResenniaDTO, EntityModel<ResenniaDTO>> {

    @Override
    public EntityModel<ResenniaDTO> toModel(ResenniaDTO resena) {
        return EntityModel.of(resena,
                linkTo(methodOn(ResenaController.class).obtenerPorId(resena.getId())).withSelfRel(),
                linkTo(methodOn(ResenaController.class).obtenerTodas()).withRel("resenas"));
    }
}