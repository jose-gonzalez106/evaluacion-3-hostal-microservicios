package com.empleado.empleados.controller.v2;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.empleado.empleados.DTO.EmpleadoDTO;
import com.empleado.empleados.assemblers.EmpleadoModelAssembler;
import com.empleado.empleados.model.Empleado;
import com.empleado.empleados.service.EmpleadoService;

import jakarta.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController("empleadoControllerV2")
@RequestMapping("/api/v2/empleados")
public class EmpleadoController {

    @Autowired
    private EmpleadoService empleadoService;

    @Autowired
    private EmpleadoModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<EmpleadoDTO>>> obtenerTodos() {
        List<EntityModel<EmpleadoDTO>> empleados = empleadoService.obtenerTodos().stream()
        .map(assembler::toModel)
        .collect(Collectors.toList());

        if (empleados.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CollectionModel.of(
            empleados,
            linkTo(methodOn(EmpleadoController.class).obtenerTodos()).withSelfRel()
        ));
    }

    @GetMapping(value = "/{rut}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<EmpleadoDTO>> obtenerPorRut(@PathVariable String rut) {
        try {
            EmpleadoDTO dto = empleadoService.buscarPorRut(rut);
            return ResponseEntity.ok(assembler.toModel(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value = "/hostal/{hostalId}/tipo/{tipoId}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<EmpleadoDTO>> crear(@PathVariable Long hostalId, @PathVariable Long tipoId, @Valid @RequestBody Empleado empleado) {
        try {
            empleadoService.guardar(hostalId, tipoId, empleado);
            EmpleadoDTO dto = empleadoService.buscarPorRut(empleado.getRut());
            return ResponseEntity
            .created(linkTo(methodOn(EmpleadoController.class).obtenerPorRut(dto.getRut())).toUri())
            .body(assembler.toModel(dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
