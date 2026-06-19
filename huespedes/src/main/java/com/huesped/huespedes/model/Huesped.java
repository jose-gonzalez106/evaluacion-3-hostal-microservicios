package com.huesped.huespedes.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "huespedes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Huesped {

    @Id
    @NotBlank(message = "El run es obligatorio")
    @Pattern(
        regexp = "^[0-9]{7,8}-[0-9Kk]$",
        message = "Formato de RUN inválido"
    )
    private String run;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 100)
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(min = 3, max = 100)
    private String apellido;

    @NotBlank(message = "El teléfono es obligatorio")
    @Pattern(
        regexp = "^[0-9]{9}$",
        message = "El teléfono debe contener 9 dígitos"
    )
    private String telefono;

    @Email(message = "Correo inválido")
    @NotBlank(message = "El correo es obligatorio")
    @Column(unique = true)
    private String correo;

    @NotNull(message = "La comuna es obligatoria")
    private Long comunaId;
}