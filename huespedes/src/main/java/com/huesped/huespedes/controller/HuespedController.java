package com.huesped.huespedes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.huesped.huespedes.DTO.HuespedDTO;
import com.huesped.huespedes.model.Huesped;
import com.huesped.huespedes.service.HuespedService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/huespedes")
public class HuespedController {

    @Autowired
    private HuespedService huespedService;

    @GetMapping
    public ResponseEntity<List<HuespedDTO>> obtenerTodos() {

        List<HuespedDTO> huespedes = huespedService.obtenerTodos();

        if (huespedes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(huespedes);
    }

    @GetMapping("/{run}")
    public ResponseEntity<HuespedDTO> obtenerPorRun(
            @PathVariable String run) {

        return ResponseEntity.ok(
                huespedService.buscarPorRun(run));
    }

    @PostMapping
    public ResponseEntity<HuespedDTO> crear(
            @Valid @RequestBody Huesped huesped) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(huespedService.guardar(huesped));
    }

    @PutMapping("/{run}")
    public ResponseEntity<HuespedDTO> actualizar(
            @PathVariable String run,
            @Valid @RequestBody Huesped huesped) {

        return ResponseEntity.ok(
                huespedService.actualizar(run, huesped));
    }

    @DeleteMapping("/{run}")
    public ResponseEntity<String> eliminar(
            @PathVariable String run) {

        return ResponseEntity.ok(
                huespedService.eliminar(run));
    }
}