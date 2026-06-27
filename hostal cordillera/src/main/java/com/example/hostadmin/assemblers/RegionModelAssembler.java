package com.example.hostadmin.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.example.hostadmin.DTO.RegionDTO;
import com.example.hostadmin.controller.v2.RegionController;

@Component
public class RegionModelAssembler 
    implements RepresentationModelAssembler<RegionDTO, EntityModel<RegionDTO>> {

    @Override
    public EntityModel<RegionDTO> toModel(RegionDTO region) {
        return EntityModel.of(region,
            linkTo(methodOn(RegionController.class).obtenerPorId(region.getId())).withSelfRel(),
            linkTo(methodOn(RegionController.class).obtenerTodas()).withRel("regiones")
        );
    }
}