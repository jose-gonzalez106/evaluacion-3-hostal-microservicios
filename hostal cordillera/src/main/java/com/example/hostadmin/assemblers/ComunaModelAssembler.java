package com.example.hostadmin.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.example.hostadmin.DTO.ComunaDTO;
import com.example.hostadmin.controller.v2.ComunaController;

@Component
public class ComunaModelAssembler
        implements RepresentationModelAssembler<ComunaDTO, EntityModel<ComunaDTO>> {

    @Override
    public EntityModel<ComunaDTO> toModel(ComunaDTO comuna) {
        return EntityModel.of(comuna,
                linkTo(methodOn(ComunaController.class).obtenerPorId(comuna.getId())).withSelfRel(),
                linkTo(methodOn(ComunaController.class).obtenerTodas()).withRel("comunas"));
    }
}
