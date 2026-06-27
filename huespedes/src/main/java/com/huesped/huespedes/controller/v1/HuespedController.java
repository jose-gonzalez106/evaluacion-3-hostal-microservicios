package com.huesped.huespedes.controller.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.huesped.huespedes.DTO.HuespedDTO;
import com.huesped.huespedes.model.Huesped;
import com.huesped.huespedes.service.HuespedService;

import jakarta.validation.Valid;

@RestController("huespedControllerV1")
@RequestMapping("/api/v1/huespedes")
public class HuespedController {

    @Autowired
    private HuespedService huespedService;

    @GetMapping
    public ResponseEntity<List<HuespedDTO>> obtenerTodos() {
        List<HuespedDTO> lista = huespedService.obtenerTodos();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/{run}")
    public ResponseEntity<?> obtenerPorRun(@PathVariable String run) {
        try {
            HuespedDTO dto = huespedService.buscarPorRun(run);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Huesped huesped) {
        try {
            HuespedDTO guardado = huespedService.guardar(huesped);
            return new ResponseEntity<>(guardado, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{run}")
    public ResponseEntity<?> actualizar(@PathVariable String run, @Valid @RequestBody Huesped huesped) {
        try {
            HuespedDTO actualizado = huespedService.actualizar(run, huesped);
            return new ResponseEntity<>(actualizado, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{run}")
    public ResponseEntity<?> eliminar(@PathVariable String run) {
        try {
            String resultado = huespedService.eliminar(run);
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
