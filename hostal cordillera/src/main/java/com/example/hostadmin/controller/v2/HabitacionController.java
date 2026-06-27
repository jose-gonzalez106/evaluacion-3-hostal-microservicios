package com.example.hostadmin.controller.v2;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hostadmin.DTO.HabitacionDTO;
import com.example.hostadmin.model.Habitacion;
import com.example.hostadmin.service.HabitacionService;

import jakarta.validation.Valid;

@RestController("HabitacionControllerV2")
@RequestMapping("/api/v2/habitaciones")
public class HabitacionController {

    @Autowired
    private HabitacionService habitacionService;

    @GetMapping
    public ResponseEntity<List<HabitacionDTO>> obtenerTodas() {
        List<HabitacionDTO> lista = habitacionService.obtenerTodas();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/{numero}")
    public ResponseEntity<?> obtenerPorNumero(@PathVariable Integer numero) {
        try {
            HabitacionDTO dto = habitacionService.buscarPorNumero(numero);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/hostal/{hostalId}")
    public ResponseEntity<?> crear(@PathVariable Long hostalId, @Valid @RequestBody Habitacion habitacion) {
        try {
            Habitacion guardada = habitacionService.guardar(hostalId, habitacion);
            return new ResponseEntity<>(guardada, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{numero}")
    public ResponseEntity<?> actualizar(@PathVariable Integer numero, @Valid @RequestBody Habitacion habitacion) {
        try {
            Habitacion actualizada = habitacionService.actualizar(numero, habitacion);
            return new ResponseEntity<>(actualizada, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{numero}/estado")
    public ResponseEntity<?> cambiarEstado(@PathVariable Integer numero, @Valid @RequestBody HabitacionDTO dto) {
        try {
            Habitacion actualizada = habitacionService.cambiarEstado(numero, dto.getEstado());
            return new ResponseEntity<>(actualizada, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{numero}")
    public ResponseEntity<?> eliminar(@PathVariable Integer numero) {
        try {
            String resultado = habitacionService.eliminar(numero);
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
