package com.huesped.huespedes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.huesped.huespedes.DTO.HuespedDTO;
import com.huesped.huespedes.model.Huesped;
import com.huesped.huespedes.service.HuespedService;

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