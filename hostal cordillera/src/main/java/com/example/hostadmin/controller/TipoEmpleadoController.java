package com.example.hostadmin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hostadmin.DTO.TipoEmpleadoDTO;
import com.example.hostadmin.model.TipoEmpleado;
import com.example.hostadmin.service.TipoEmpleadoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/tipos-empleado")
public class TipoEmpleadoController {

    @Autowired
    private TipoEmpleadoService tipoEmpleadoService;

    @GetMapping
    public ResponseEntity<List<TipoEmpleadoDTO>> obtenerTodos() {
        List<TipoEmpleadoDTO> lista = tipoEmpleadoService.obtenerTodos();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        try {
            TipoEmpleadoDTO dto = tipoEmpleadoService.buscarPorId(id);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody TipoEmpleado tipo) {
        try {
            TipoEmpleado guardado = tipoEmpleadoService.guardar(tipo);
            return new ResponseEntity<>(guardado, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            String resultado = tipoEmpleadoService.eliminar(id);
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}