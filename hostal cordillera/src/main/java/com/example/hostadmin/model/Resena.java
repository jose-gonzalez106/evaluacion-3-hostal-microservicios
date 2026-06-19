package com.example.hostadmin.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "resenas")
@Data
public class Resena {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El comentario es obligatorio")
    @Size(max = 500, message = "La reseña no puede superar los 500 caracteres")
    @Column(nullable = false, length = 500)
    private String comentario;

    @Min(value = 1, message = "La calificación mínima es 1")
    @Max(value = 5, message = "La calificación máxima es 5")
    @Column(nullable = false)
    private Integer calificacion;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(name = "huesped_run", nullable = false)
    private String huespedRun;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hostal_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "comuna"})
    private Hostal hostal;
}