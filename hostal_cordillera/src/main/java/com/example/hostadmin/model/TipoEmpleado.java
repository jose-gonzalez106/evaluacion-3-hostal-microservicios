package com.example.hostadmin.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "tipos_empleado")
@Data
public class TipoEmpleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El cargo es obligatorio")
    @Size(min = 3, max = 50, message = "El cargo debe tener entre 3 y 50 caracteres")
    @Column(nullable = false, unique = true, length = 50)
    private String categoria;
}